package net.termer.twist;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.termer.twist.document.DocumentBuilder;
import net.termer.twist.utils.Config;
import net.termer.twist.utils.Writer;

public class Twist {
	public static HashMap<String,String> settings = null;
	public static HashMap<String,String> linkedDomains = null;
	
	public static void main(String[] args) {
		// Setup files and directories
		
		// Domains directory
		File domainsDir = new File("domains/");
		if(!domainsDir.exists()) {
			domainsDir.mkdirs();
		}
		
		// 404 Error Page
		File notfoundFile = new File("404.html");
		if(!notfoundFile.exists()) {
			try {
				notfoundFile.createNewFile();
				Writer.print("<html><head><title>404 Not Found</title></head><body><h1>404 Not Found</h1><p>File could not be located on this server.</p></body></html>", notfoundFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		// Settings file
		File settingsFile = new File("twist.ini");
		if(!settingsFile.exists()) {
			try {
				settingsFile.createNewFile();
				Writer.print("# IP address to bind to\n"+
						"ip: 127.0.0.1\n\n"+
						"# Port to run server on\n"+
						"port: 2003\n\n"+
						"# Java Keystore path to use to enable HTTPS\n"+
						"# Leave commented out to disable HTTPS\n"+
						"#keystore: keystore.jks\n\n"+
						"# Keystore password\n"+
						"#keystore-password: drowssap\n\n"+
						"# Enable/Disable logging\n"+
						"logging: true", settingsFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		// Linked Domains file
		File linkedFile = new File("linkeddomains.ini");
		if(!linkedFile.exists()) {
			try {
				linkedFile.createNewFile();
				Writer.print("# You can specify domains that will\n"+
							 "# use another domains's file system\n"+
							 "# by using a variant of the below:\n"+
							 "127.0.0.1 > localhost\n"+
							 "# The above specified that when\n"+
							 "# users connect to 127.0.0.1 they\n"+
							 "# will be served with the same\n"+
							 "# filesystem system as they would\n"+
							 "# if they connected to localhost", linkedFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		// Global Static files directory
		File globalstaticFile = new File("globalstatic/");
		if(!globalstaticFile.exists()) {
			globalstaticFile.mkdirs();
		}
		
		// Set global static files location
		staticFiles.externalLocation(globalstaticFile.getAbsolutePath());
		
		// Parse settings
		try {
			settings = Config.parseConfig(settingsFile, ":", "#");
		} catch(IOException e) {
			System.err.println("=========================\n"+
							   "Failed to parse settings\n"+
							   "=========================");
			e.printStackTrace();
		}
		
		// Parse linked domains
		try {
			linkedDomains = Config.parseConfig(linkedFile, ">", "#");
		} catch(IOException e) {
			System.err.println("===============================\n"+
							   "Failed to parse linked domains\n"+
							   "===============================");
			e.printStackTrace();
		}
		
		// Set port to run on
		if(settings.containsKey("port")) {
			port(Integer.parseInt(settings.get("port")));
		} else {
			port(2003);
		}
		// Set IP address to bind to
		if(settings.containsKey("ip")) {
			ipAddress(settings.get("ip"));
		} else {
			ipAddress("127.0.0.1");
		}
		// Enable HTTPS if keystore is available
		if(settings.containsKey("keystore") && settings.containsKey("keystore-password")) {
			File ks = new File(settings.get("keystore"));
			if(ks.exists()) {
				secure(settings.get("keystore"),settings.get("keystore-password"),null,null);
			}
		}
		
		get("*", (req, res) -> {
			if(settings.containsKey("logging")) {
				if(settings.get("logging").toLowerCase().startsWith("t")) {
					String ln = new java.util.Date().toString()+": GET "+req.pathInfo()+" ("+req.ip()+" "+req.userAgent()+")";
					System.out.println(ln);
				}
			}
			String domain = req.url();
			if(domain.toLowerCase().startsWith("http://")) {
				domain=domain.substring(7);
			} else if(domain.toLowerCase().startsWith("https://")) {
				domain=domain.substring(8);
			}
			domain=domain.replaceAll(req.pathInfo(),"");
			if(domain.contains(":")) {
				domain=domain.split(":")[0];
			}
			String r = "";
			if(new File("domains/"+domain+"/"+req.pathInfo()).isDirectory()) {
				if(!req.pathInfo().endsWith("/")) {
					res.redirect(req.pathInfo()+"/");
				} else {
					r = DocumentBuilder.loadDocument(domain, req.pathInfo());
				}
			} else {
				r = DocumentBuilder.loadDocument(domain, req.pathInfo());
			}
			
			return r;
		});
	}
}

package net.termer.twist.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.termer.twist.Twist;

public class DocumentBuilder {
	public static String loadDocument(String domain, String path) throws IOException {
		String r = "";
		try {
		if(Twist.linkedDomains.containsKey(domain)) {
			domain = Twist.linkedDomains.get(domain);
		}
		if(new File("domains/"+domain+"/").exists()) {
			if(path.startsWith("/")) {
				path = path.substring(1);
			}
			if(path.length()<1) {
				path="index.html";
			}
			if(path.endsWith("/")) {
				path+="index.html";
			}
			if(new File("domains/"+domain+"/"+path).isDirectory()) {
				if(!path.endsWith("/")) {
					path+='/';
				}
				path+="index.html";
			}
			File document = new File("domains/"+domain+"/"+path);
			if(document.isDirectory()) {
				if(!path.endsWith("/")) {
					path+='/';
				}
				path+="index.html";
			}
			if(document.exists()) {
				File top = new File("domains/"+domain+"/top.html");
				File bottom = new File("domains/"+domain+"/bottom.html");
				if(top.exists()) {
					r=readFile(top.getPath());
				}
				r+=readFile(document.getPath());
				if(bottom.exists()) {
					r+=readFile(bottom.getPath());
				}
			} else {
				r = readFile("404.html");
			}
		} else {
			r = readFile("404.html");
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	public static String readFile(String path) throws IOException {
		String r = "";
		FileInputStream fin = new FileInputStream(new File(path));
		while(fin.available()>0) {
			r+=(char)fin.read();
		}
		fin.close();
		return r;
	}
}

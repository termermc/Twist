package net.termer.twist.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Config {
	public static HashMap<String,String> parseConfig(File configFile, String separate, String comment) throws IOException {
		HashMap<String,String> map = new HashMap<String,String>();
		// Setup readers
		FileInputStream fin = new FileInputStream(configFile);
		InputStreamReader inputReader = new InputStreamReader(fin);
		BufferedReader reader = new BufferedReader(inputReader);
		
		while(reader.ready()) {
			String ln = reader.readLine();
			if(!ln.startsWith(comment)) {
				if(ln.contains(separate)) {
				String field = ln.split(separate)[0].trim();
					if(!map.containsKey(field)) {
						String value = ln.substring(ln.indexOf(separate)+1).trim();
						map.put(field, value);
					}
				}
			}
		}
			
		// Close all readers
		reader.close();
		inputReader.close();
		fin.close();
		
		return map;
	}
}

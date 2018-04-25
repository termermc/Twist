package net.termer.twist.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {
	public static void print(String str, File file) throws IOException {
		FileOutputStream fout = new FileOutputStream(file);
		for(int i = 0; i < str.length(); i++) {
			fout.write((int)str.charAt(i));
		}
		fout.close();
	}
	
	public static void println(String str, File file) throws IOException {
		print(str+'\n',file);
	}
}

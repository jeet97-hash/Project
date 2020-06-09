package com.project.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {

	public Properties props;
	
	public ReadPropertiesFile(String path) {
		//To read properties file from specified path
		try {
			FileInputStream fin = new FileInputStream(new File(path));
			props = new Properties();
			props.load(fin);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to load Configuration file: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Unable to load Configuration file: "+e.getMessage());
		}
	}
	
	public String getConfigData(String key) {
		return props.getProperty(key);
	}
}

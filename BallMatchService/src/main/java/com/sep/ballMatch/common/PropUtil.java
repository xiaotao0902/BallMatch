package com.sep.ballMatch.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropUtil {
	
	private static Properties props;
	private final static Logger logger = LogManager.getLogger(PropUtil.class);
	
	static {
		try {
			props = loadProperties();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public static String getProperty(String propKey){
		if(propKey != null && !propKey.equals("")){
			if(props != null)
				return props.getProperty(propKey);
		}
		return null;
	}
	
	private static Properties loadProperties() throws IOException{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("config.properties");
		if(url == null){
			return null;
		}
			
		FileInputStream inStream = null;
		try {
			File file = new File(url.getFile());
			props = new Properties();
			inStream = new FileInputStream(file);
			props.load(inStream);
		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
			
		return props;
	}

	/**
	 * @return the props
	 */
	public static Properties getProps() {
		return props;
	}
}
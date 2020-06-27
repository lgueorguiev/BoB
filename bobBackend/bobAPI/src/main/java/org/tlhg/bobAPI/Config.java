package org.tlhg.bobAPI;

public class Config {
	public static String getParam(String paramName) {
		String prop = System.getProperty(paramName);
		return(prop != null) ? prop : System.getenv(paramName);
	}
}

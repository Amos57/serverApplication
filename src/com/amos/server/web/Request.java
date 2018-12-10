package com.amos.server.web;

import java.util.Map;

public interface Request {

	
	
	/**
	 * get value from html input
	 */
	String getParametr(String name);
	
	String getURL();
	
	Map<String, String> getParametrs();
	
	
	int getContentLenght();
	
	
	String getContentType();
	
	
	
	String getRemoteAddr();
	
	
	
	String getRemoteUser();
	
	
	String getUserAgent();
	
	
	String getMethod();
	
}

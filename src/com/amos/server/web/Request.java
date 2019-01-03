p	/**
	 * get value from html input
	 */
	String getParametr(String name);
	
	String getURL();
	
	Map<String, String> getParametrs();
	
	
	int getContentLenght();
	
	
	String getContentType();
	
	
	
	String getRemoteAddr();
	
	JSONObject getJsonObject(String name);
	
	JsonArray<?> getJsonArray(String name);
	
	String getRemoteUser();
	
	
	String getUserAgent();
	
	
	String getMethod();

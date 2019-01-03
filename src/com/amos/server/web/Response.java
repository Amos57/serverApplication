package com.amos.server.web;

import java.io.PrintWriter;

public interface Response {

	PrintWriter getWriter();
	
	void addHeader(String name,Object value);
	
	void sendRedirect(String pathURL);
	
	void setEncoding(String charset);

	int  getContentLenght();

	void send();
	
	void sendJsonObject(JSONObject jsonObject);
	
}

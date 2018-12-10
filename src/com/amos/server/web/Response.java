package com.amos.server.web;

import java.io.PrintWriter;
import java.nio.charset.Charset;

import com.amos.server.annotation.HttpServet;
import com.amos.server.servlets.Page;

public interface Response {

	
	
	
	PrintWriter getWriter();
	
	void   addHeader(String name,Object value);
	
	void sendRedirect(String pathURL);
	
	void setEncoding(Charset charset);

	int getContentLenght();

	//temp
	void send();

	//void addServlet(HttpServet...httpServets);
	
}

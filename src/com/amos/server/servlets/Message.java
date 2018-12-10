package com.amos.server.servlets;

import java.nio.charset.StandardCharsets;

import com.amos.server.annotation.HttpServet;
import com.amos.server.annotation.Servlet;
import com.amos.server.web.Request;
import com.amos.server.web.Response;

@Servlet(url="message")
public class Message extends HttpServet{

	private String name;
	private String email;
	private String message;
	
	@Override
	public void doPost(Request request, Response response) {
       name=request.getParametr("name");
       email=request.getParametr("email");
       message=request.getParametr("message");
       response.setEncoding(StandardCharsets.UTF_8);
	    response.getWriter().println(toString());
	}
	
	@Override
	public void doGet(Request request, Response response) {
		doPost(request, response);
      
	}
	
	@Override
	public String toString() {
		return "{\"name\":\""+name+"\",\"email\":\""+email+"\",\"message\":\""+message+"\"}";
	}

}

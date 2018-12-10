package com.amos.server.servlets;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.amos.server.annotation.HttpServet;
import com.amos.server.annotation.Servlet;
import com.amos.server.web.Request;
import com.amos.server.web.Response;


@Servlet(url="user")
public class User extends HttpServet{
   
	
	
	@Override
	public void doGet(Request request, Response response) {
/*	  
	  pw.println("<h1>Hello world<h1>");
	  pw.flush();*/
		response.setEncoding(StandardCharsets.UTF_8);
	  response.sendRedirect("index.html");
	}
	@Override
	public void doPost(Request request, Response response) {
		PrintWriter pw=response.getWriter();
		pw.println(toString());
	}
	
	private String login;
	private String password;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
	
		return login+" "+password;
	}

	
	
}

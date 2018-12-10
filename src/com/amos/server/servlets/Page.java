package com.amos.server.servlets;

import java.nio.charset.StandardCharsets;

import com.amos.server.annotation.HttpServet;
import com.amos.server.annotation.Servlet;
import com.amos.server.web.Request;
import com.amos.server.web.Response;

@Servlet(url="people")
public class Page extends HttpServet{


@Override
public void doPost(Request request, Response response) {

	age=request.getParametr("login");
	name=request.getParametr("password");
	
	
}

@Override
	public void doGet(Request request, Response response) {
	response.setEncoding(StandardCharsets.UTF_8);
    response.getWriter().println(toString());
 
	}

	private String age;
	private String name;
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "{\"name\":\""+name+"\",\"age\":\""+
				age+"\"}";
	}
	
}

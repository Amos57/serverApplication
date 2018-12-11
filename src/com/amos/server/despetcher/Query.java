package com.amos.server.despetcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.amos.server.annotation.HttpServet;
import com.amos.server.web.HttpRequest;
import com.amos.server.web.HttpRespons;
import com.amos.server.web.Request;
import com.amos.server.web.Response;

public class Query {

	private Request request;
	private Response response;
	private HttpServet servet;
	private ServletLoader sl= ServletLoader.getInstance();
	public Query(Socket socket) throws IOException {
		
		BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		request= new HttpRequest(br);
		response= new HttpRespons(bw);
		
	}


	public Request getRequest() {
		return request;
	}


	public Response getResponse() {
		return response;
	}

    private boolean goServlet(){
    	servet=sl.getServlet(request.getURL());
    	if(servet!=null){
    		if(request.getMethod().equals("GET"))
    			servet.doGet(request, response);
    		else if(request.getMethod().equals("POST"))
    			servet.doPost(request, response);
    		return true;
    	}
		return false;
    	
    }


public void forward(){
	if(!goServlet()){
		response.setEncoding(StandardCharsets.UTF_8);
		response.sendRedirect(request.getURL());
	}
	response.send();
}

	
}

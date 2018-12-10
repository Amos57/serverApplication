package com.amos.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request{

	
	private static Map<String, String> valuesFromInputs;
	private StringBuilder values= new StringBuilder();
	private int contentLenght;
	private BufferedReader input;
	private String method;
	private String userAgent; 
	private String contentType;
	private String urlRequst;
	
	
	public HttpRequest(BufferedReader input) throws IOException {
	  this.input=input;
		
	  String line=input.readLine();
	  if(line!=null) {
	  System.out.println(line);
	  method=line.substring(0,line.indexOf(" "));
	  urlRequst=line.substring(method.length()+2,line.lastIndexOf(" "));
	  }
	  while(!(line=input.readLine()).equals("")){
		  
		  String key=line.substring(0,line.indexOf(" "));
		  System.out.println(line);
		  switch (key) {
		  
		case "User-Agent:":
			userAgent=line.substring(key.length()+1);
			break;
		case "Content-Type:":	
			contentType=line.substring(key.length()+1);
			break;	
		case "Content-Length:":	
			contentLenght=Integer.parseInt(line.substring(key.length()+1));
			break;
		default:
			break;
			
		}
		  
	  }
		
	  for(int i=0;i<contentLenght;i++){
		  values.append((char)input.read());
	  }
		if(values.length()!=0)
			valuesFromInputs=getParameters();
	}
	
	
	
	@Override
	public String getParametr(String name) {
		if(name!=null & valuesFromInputs!=null)
		return valuesFromInputs.get(name);
		 return null;
	}

	@Override
	public Map<String, String> getParametrs() {
		return valuesFromInputs;
	}

	@Override
	public int getContentLenght() {
		return contentLenght;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getRemoteAddr() {
		return null;
	}

	@Override
	public String getRemoteUser() {
		return null;
	}

	@Override
	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public String getMethod() {
		return method;
	}
	
	private Map<String,String> getParameters(){
		if(contentLenght==0)
			return new HashMap<>();
    	Map<String,String> temp= new HashMap<>();
    	String p=values.toString();
    	int aper=p.indexOf("&");
    	int eq =p.indexOf("=");
    	boolean endAppersand = false;
    	int count = 0;
    	while(!endAppersand) {
    		p=p.substring(count,p.length());
    		aper=p.indexOf("&");
    		eq =p.indexOf("=");
    		if(aper==-1) {
    		  endAppersand=true;
    		  aper=p.length();
    		}
    		String key=p.substring(0,eq);
    		String value=p.substring(eq+1,aper);
    		temp.put(key, value);
    		
    		count=aper+1;
    		
    	}
		return temp;
	}

	@Override
	public String getURL() {
		return urlRequst;
	}
}

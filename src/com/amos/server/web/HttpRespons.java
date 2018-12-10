package com.amos.server.web;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.amos.server.annotation.HttpServet;

public class HttpRespons implements Response{

	
	
	public static final int NOT_FOUNT = 404;
	private final String header="HTTP/1.1 200 OK\r\n"
		//	+ "Content-Type: text/html\r\n"
			+ "\r\n";
	private PrintWriter pw;
	private StringBuilder file=new StringBuilder();
	private StringBuilder headers= new StringBuilder();
	private Charset charset;
	public HttpRespons(BufferedWriter out) {
		pw= new PrintWriter(out,false);
		headers.append(header);
		 pw.println(headers);
		
	}
	
	@Override
	public PrintWriter getWriter() {
		return pw;
	}
    public String getPage(String file){
    	String[] temp=file.split("/");
    	return temp[temp.length-1];
    } 
	
	@Override
	public void addHeader(String name, Object value) {
		headers.delete(headers.length()-2,headers.length());
		String data=String.format("%s: %s\r\n\r\n",name, value);
		headers.append(data);
		       
	}

	@Override
	public void sendRedirect(String pathURL) {
		if(charset==null)
			charset=StandardCharsets.UTF_16;
	/*	try {
			byte[] array = Files.readAllBytes(Paths.get(pathURL));
		} catch (IOException e1) {}*/
/*		if(pathURL.endsWith("css")) addHeader("Content-Type", "text/css");
		else if(pathURL.endsWith("html") || !pathURL.contains(".")) addHeader("Content-Type", "text/html");*/

		
		
		try(FileInputStream fis= new FileInputStream(pathURL);
				InputStreamReader writer = new InputStreamReader(fis,charset)){
			int i;
			while((i=writer.read())!=-1){
				file.append((char)i);
			}
			
		}catch (IOException e) {
			try(FileInputStream fis= new FileInputStream("404.html");
					InputStreamReader writer = new InputStreamReader(fis,charset)){
				int i;
				while((i=writer.read())!=-1){
					file.append((char)i);
				}

		        }catch (Exception e2) {}
			}	

	    pw.println(file);
	
	}

	   
	
	

	public int getContentLenght(){
		return file.length();
	}
	public void send(){
		pw.flush();
		pw.close();
	}

	@Override
	public void setEncoding(Charset charset) {
		this.charset=charset;
		
	}
}

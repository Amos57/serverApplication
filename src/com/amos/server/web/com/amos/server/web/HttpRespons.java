package com.amos.server.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpRespons implements Response{

	public static final int NOT_FOUNT = 404;

	private final String header = "HTTP/1.1 200 OK\r\n";

	private PrintWriter pw;
	private StringBuilder file = new StringBuilder();
	private StringBuilder headers = new StringBuilder();
	private String charset="UTF-8";

	private boolean redirect;
	
	
	public HttpRespons(BufferedWriter out) {
		pw = new PrintWriter(out, false);
		headers.append(header);
		redirect=false;
	}

	@Override
	public PrintWriter getWriter() {
		if(!redirect){
		addHeader();
		redirect=true;
		}
		return pw;
	}

	public String getPage(String file) {
		String[] temp = file.split("/");
		return temp[temp.length - 1];
	}

	@Override
	public void addHeader(String name, Object value) {
		String data = String.format("%s: %s\r\n", name, value);
		headers.append(data);
	}

	@Override
	public void sendRedirect(String pathURL) {
		addHeader();
		try {
			file.append(new String(Files.readAllBytes(Paths.get(pathURL)), charset));
		} catch (IOException e1) {
			try {
				file.append(new String(Files.readAllBytes(Paths.get("404.html")), charset));
			} catch (IOException e) {
			}
		}
		pw.println(file);
	}

	public int getContentLenght() {
		return file.length();
	}

	public void send() {
		pw.flush();
		pw.close();
	}

	@Override
	public void setEncoding(String charset) {
		this.charset = charset;

	}


	private void addHeader() {
		pw.println(headers.append("\r\n").toString());

	}

	@Override
	public void sendJsonObject(JSONObject jsonObject) {
		addHeader();
		pw.println(jsonObject);
	}


	
}

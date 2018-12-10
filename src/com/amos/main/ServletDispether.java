package com.amos.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ServletDispether implements HttpRequest ,HttpRespons{

	private final String header="HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
	private PrintWriter pw;
	private int contentLenght;
	private Map<String,String> inputs;
	private StringBuilder body = new StringBuilder();
	private boolean isRespons;
	private StringBuilder responsServer=new StringBuilder();
	
	public ServletDispether(BufferedReader in,BufferedWriter out) throws IOException {
       pw= new PrintWriter(out);
       
       
       String line= in.readLine();
       System.out.println(line);
       if(line==null) return;
       isRespons = line.startsWith("POST");
       while (!(line = in.readLine()).equals("")) {
    	   System.out.println(line);
        String temp=line.substring(0,line.indexOf(" "));
    	   switch (temp) {
    	   
		case "Content-Length:":
			contentLenght=Integer.parseInt(line.substring("Content-Length: ".length()));
			break;

		default:
			break;
		}
       }
       

       if(isRespons) {
	   
	        for (int i=0;i<contentLenght;i++) {
	            body.append((char) in.read());
	      }
         inputs=getParameters();
    //     isRespons=false;
       } 
       responsServer.append(header);
       pw.println(responsServer.toString());
	}
	
	public boolean isRespons() {
		return isRespons;
	}
	@Override
	public PrintWriter getWriter() {
		return pw;
	}

	@Override
	public void senrRedirect(String path) {
        
	}

	@Override
	public void addHeader(String name, String value) {
		responsServer.append(String.format("%s: %s\r\n", name,value));
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContentLenght() {
		return contentLenght;
	}

	@Override
	public String getParametr(String param) {
		if(inputs!=null)
		   return inputs.get(param);
		return null;
	}	
	
    public void send() {
    	pw.flush();
    	pw.close();
    }
    private Map<String,String> getParameters(){
    	Map<String,String> temp= new HashMap<>();
    	String p=body.toString();
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

	public void setRespons(boolean b) {
		this.isRespons=b;
		
	}
}

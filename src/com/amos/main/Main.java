package com.amos.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;




public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
        
	
		 ServerSocket sss = null;
sss = new ServerSocket(1234);
  	while(true) {
      
     
        	
      
    try {
    	 Socket socket=sss.accept();
    	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    	    ServletDispether sd= new ServletDispether(in, out);
    	    PrintWriter pw=sd.getWriter();
    	    
            pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
                if(sd.isRespons()) {
                	
                	pw.println("<h1>Вы напечатали:"+sd.getParametr("name")+sd.getParametr("age")+"</h1>");
                    pw.println("<p><a href='http://localhost:1234' style='text-decoration:none;'>Back</a></p>");
    	    sd.setRespons(false);
                } else {   	
               
    	    pw.println("<form method='POST'>");
    	    pw.println("<input name='name' type='text'/>");
    	    pw.println("<input name='age' type='text'/>");
    	    pw.println("<input type='submit'/>");
    	    pw.println("</form>");
                }
    	    sd.send();
    	   socket.close();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    StringWriter sw = new StringWriter();
    	    e.printStackTrace(new PrintWriter(sw));
    	}

      
}
	}


}
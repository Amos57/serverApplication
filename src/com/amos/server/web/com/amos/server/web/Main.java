package com.amos.server.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.amos.server.despetcher.Query;

public class Main {

	

	
	public static void main(String[] args) throws IOException {


		@SuppressWarnings("resource")
		ServerSocket ss= new ServerSocket(8080);

		while(true){
			
			Socket socket=ss.accept();
			Query query= new Query(socket);
	
			query.forward();
			socket.close();

		}

	}
	

}

package com.amos.server.php;

/***********************************************************
*                    JAVA WEB SERVER                       *
*                                                          *
* File: WebServer.java                                     *
* Description: Main server thread. Runs a loop for a       *
*     connection accepted. Starts ClientCommunicator       *
*     thread for the established connection.               *
* Last modified: 10.08.2001                                *
***********************************************************/

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer extends Thread {
   
   MessageBoard message_board;
   boolean status;
   
   public WebServer (MessageBoard mb) {
      message_board = mb;
      status = true; // Server thread is running
   }

   public void run () {
      message_board.AddTimedMessage ("Server thread started.\n");
      /* Connection waiting cycle */
      message_board.AddTimedMessage ("Waiting for the connection...\n");
      while (status) {
         try {
            // Binding to the 80th port
            ServerSocket server_socket = new ServerSocket (80);
            // message_board.AddTimedMessage ("Waiting for the connection...\n");
            Socket sock = server_socket.accept ();
            InetAddress inet_address = sock.getInetAddress ();
            message_board.AddTimedMessage ("Connection accepted at: " + inet_address.getHostName () + "\n");
            /* Connection is established here */
            // Now we have to pass the socket to client communicator
            ClientCommunicator cc = new ClientCommunicator (sock, message_board);
            cc.start ();
            server_socket.close ();
         }
         catch (IOException e) {
            message_board.AddMessage ("(!) Socket exception: " + e.getMessage () + "\n");
            status = false;
         }
      }
      terminate (); // Stop the server.
   }
   
   public void terminate () {
      message_board.AddTimedMessage ("Server thread shutted down.\n");
      status = false;
   }
   
   public boolean isRunning () {
      return status;
   }
   

}

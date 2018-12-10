package com.amos.server.php;

/***********************************************************
*                    JAVA WEB SERVER                       *
*                                                          *
* File: Server.java                                        *
* Description: Main module. Starts main server thread      *
*     for a connection waiting.                            *
* Last modified: 10.08.2001                                *
***********************************************************/

import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Server {

   public static WebServer web_server;

   public static void main (String args[]) {
      Frame main_window = new Frame ("Java Server Console");
      main_window.resize (400, 300);
      MessageBoard message_board = new MessageBoard ("* JAVA WEB SERVER *\n");
      System.out.println ("* JAVA WEB SERVER *");
      System.out.println ("");
      System.out.println ("Press Ctrl+C in this window to stop the server.");
      message_board.AddMessage ("\n");
      main_window.add (message_board);
      main_window.show ();
      // Server directory checking
      try {
         File tmpfile = new File ("server.cfg");
         String subpath = new String (tmpfile.getAbsolutePath ());
         subpath = subpath.substring (0, subpath.indexOf ("server.cfg", 0));
         FileWriter fw = new FileWriter ("D://dir.tmp");
         fw.write (subpath);
         fw.close ();
      }
      catch (IOException e) {
         message_board.AddMessage ("(!) Can't create server directory file\n");
      }
      web_server = new WebServer (message_board);
      web_server.start ();
      while (web_server.isRunning ()){
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
      } // Waiting for the server termination.
      File fff = new File ("D://dir.tmp");
      fff.delete ();
      message_board.AddMessage ("Server is stopped.\n");
   }
   
}

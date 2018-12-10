package com.amos.server.php;

/***********************************************************
*                    JAVA WEB SERVER                       *
*                                                          *
* File: ClientCommunicator.java                            *
* Description: Establishes input/output stream for the     *
*     client connection. Parses HTTP requests and          *
*     sends appropriate data.                              *
* Last modified: 10.08.2001                                *
***********************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;


public class ClientCommunicator extends Thread {
   
   Socket sock;
   InputStream input_stream;
   OutputStream output_stream;
   MessageBoard message_board;
   boolean status;
   String input_buffer;
   String output_buffer;

   public ClientCommunicator (Socket s, MessageBoard mb) {
      sock = s;
      message_board = mb;
      status = true;
   }
   
   public void ReadData () {
      char tmp_ch[];
      int data;
      
      tmp_ch = new char [1];
      try {
         data = input_stream.read ();
         tmp_ch[0] = (char)(data);
         input_buffer = input_buffer.concat (new String (tmp_ch));
      }
      catch (IOException e) {
         message_board.AddMessage ("(!) ReadData exception: " + e.getMessage ());
         status = false;
      }
   }
   
   public void ClearInputBuffer () {
      input_buffer = "";
   }
   
   public void WriteData (String d) {
      output_buffer = output_buffer.concat (d);
   }
   
   public void WriteByteData (byte buffer[]) {
      try {
         output_stream.write (buffer);
      }
      catch (IOException e) {
         message_board.AddMessage ("(!) WriteByteData exception: " + e.getMessage () + "\n");
         status = false;
      }
   }
   
   public void ClearOutputBuffer () {
      output_buffer = "";
   }
   
   public void FlushOutputBuffer () {
      if (output_buffer.length () > 0)
         try {
            message_board.AddTimedMessage ("Sending data...\n");
            for (int i = 0; i < output_buffer.length (); i++)
               output_stream.write ((int)(output_buffer.charAt (i)));
         }
         catch (IOException e) {
            message_board.AddMessage ("(!) FlushOutputBuffer exception: " + e.getMessage () + "\n");
            status = false;
         }
      ClearOutputBuffer ();
   }

   public boolean CheckHTTPRequest () {
      if ((input_buffer.charAt (input_buffer.length () - 1) == (char)(10)) &&
          (input_buffer.charAt (input_buffer.length () - 2) == (char)(13)) &&
          (input_buffer.charAt (input_buffer.length () - 3) == (char)(10)) &&
          (input_buffer.charAt (input_buffer.length () - 4) == (char)(13))) {
         //message_board.AddMessage ("* REQUEST ACCEPTED *\n");
         return true;
      }
      else
         return false;
   }
   
   public String getResource (String resource) {
      String path;
      String m_types[];
      String index_files[];
      
      String DocRoot = new String ("htdocs");
      String SysRoot = new String ("system");
      
      m_types = new String [6];
      m_types[0] = new String (".html");
      m_types[1] = new String (".htm");
      m_types[2] = new String (".php");
      m_types[3] = new String (".gif");
      m_types[4] = new String (".jpg");
      m_types[5] = new String (".css");

      index_files = new String [3];
      index_files[0] = "index.html";
      index_files[1] = "index.htm";
      index_files[2] = "index.php";
      
      // Checking for the registered type
      boolean is_m_type = false;
      for (int i = 0; i < m_types.length; i++)
         if (resource.endsWith (m_types[i])) {
            is_m_type = true;
            break;
         }
         
      path = new String ("");
      if ((!is_m_type) && (!resource.endsWith ("/")))
         resource = resource.concat ("/");
         
      // Now checking for index files...
      boolean good_path = false;
      if (resource.endsWith ("/")) {
         for (int i = 0; i < index_files.length; i++) {
            path = new String (DocRoot + resource.concat (index_files[i]));
            try {
               FileInputStream fis = new FileInputStream (path);
               fis.close ();
               // Opened Ok.
               good_path = true;
               break;
            }
            catch (IOException e) {
               // Can't open :(
            }
         }
      } // if ends with "/"
      else {
         path = new String (DocRoot.concat (resource));
         try {
            FileInputStream fis = new FileInputStream (path);
            fis.close ();
            // Opened Ok.
            good_path = true;
         }
         catch (IOException e) {
            // Can't open :(
         }
      } //---
      if (!good_path)
         path = SysRoot.concat ("/not_found.html");
      
      return (path);
   }

   public void ParseHTTPRequest () {
     // String resource;
      String res;
     /* String DocRoot;
     String SystemRoot;*/
      String path;
      String variables;
      String var_name;
      String var_value;
      FileInputStream f;
      int total;
      byte bbuffer[];
      
    /*  DocRoot = new String ("htdocs");
      SystemRoot = new String ("system");*/
      
      if (input_buffer.startsWith ("GET ")) {
         input_buffer = input_buffer.substring (4);
         res = input_buffer.substring (0, input_buffer.indexOf (" ", 0));
         message_board.AddTimedMessage ("RESOURCE REQUESTED: " + res + "\n");
         if (res.indexOf ("?", 0) != -1) {
            variables = res.substring (res.indexOf ("?", 0) + 1);
            res = res.substring (0, res.indexOf ("?", 0));
            message_board.AddTimedMessage ("VARIABLES PASSED: " + variables + "\n");
            // Getting resource...
            path = getResource (res);
            message_board.AddTimedMessage ("RESOURCE FOUND: " + path + "\n");         
            // Creating temporaly php file for variables setting...
            try {
               FileWriter fw = new FileWriter ("C:\\temp.php");
               fw.write ("<?\n");
               while (variables.length () > 0) {
                  var_name = variables.substring (0, variables.indexOf ("=", 0));
                  variables = variables.substring (variables.indexOf ("=", 0) + 1);
                  if (variables.indexOf ("&", 0) == -1) { // the last variable
                     var_value = new String (variables);
                     variables = new String ("");
                  }
                  else {
                     var_value = variables.substring (0, variables.indexOf ("&", 0));
                     variables = variables.substring (variables.indexOf ("&", 0) + 1);
                  }
                  // Now we have to make some changes in var_value
                  String old_value = var_value.replace ('+', ' ');
                  var_value = new String ("");
                  int spos = 0;
                  while (spos < old_value.length ()) {
                     if (old_value.charAt (spos) == '%') {
                        String hexval = old_value.substring (spos + 1, spos + 3);
                        Integer tmpi = new Integer (0);
                        char chi[];
                        chi = new char[1];
                        chi[0] = (char)(tmpi.parseInt (hexval, 16));
                        var_value = var_value.concat (new String (chi));
                        spos+= 3;
                     }
                     else {
                        var_value = var_value.concat (old_value.substring (spos, spos + 1));
                        spos++;
                     }
                  }
                  fw.write ("   $" + var_name + "=\"" + var_value + "\";\n");
               }
               
               File tmpfile = new File ("server.cfg");
               String subpath = new String (tmpfile.getAbsolutePath ());
               subpath = subpath.substring (0, subpath.lastIndexOf ("\\"));
               //message_board.AddMessage (" ----> " + subpath + "\n");
               fw.write ("   $JAVA_SERVER_PATH =\"" + subpath + "\\\\\";\n");
               fw.write ("   require \"" + subpath + "\\" + path + "\";\n");
               fw.write ("?>\n");
               fw.close ();
               
               String ExecStr = new String ("php\\php.bat C:\\temp.php");
               message_board.AddTimedMessage ("EXECUTING: " + ExecStr + "...\n");
               try {
                  Process ppp = Runtime.getRuntime().exec (ExecStr);
                  // Waiting for the process termination...
                  try {
                     ppp.waitFor ();
                  }
                  catch (InterruptedException e) {}
                  try {
                     f = new FileInputStream ("C:\\php.out");
                     total = f.available ();
                     bbuffer = new byte [total];
                     f.read (bbuffer);
                     f.close ();
                     // Writing data...
                     message_board.AddTimedMessage ("DONE.\n");
                     WriteData ("HTTP/1.1 200 OK\n");
                     FlushOutputBuffer ();
                     WriteByteData (bbuffer);
                     // Delete temporaly files...
                     File fff = new File ("C:\\php.out");
                     fff.delete ();
                     fff = new File ("C:\\temp.php");
                     fff.delete ();
                  }
                  catch (IOException e) {
                     message_board.AddMessage ("(!) PHP output file open error: " + e.getMessage () + "\n");
                     WriteData ("<H1>C:\\php.out is not found</H1>\n");
                  }                  
               }
               catch (IOException e) {
                  message_board.AddMessage ("(!) Exec exception: " + e.getMessage () + "\n");
                  WriteData ("<H1>PHP script execution error</H1>\n");
               }
            }
            catch (IOException e) {
               message_board.AddMessage ("(!) PHP temporaly file creation exception: " + e.getMessage () + "\n");
               WriteData ("<H1>Server internal error</H1>");
            }
         }
         else { // No variables passed...
            /* Reserved file types */
            path = getResource (res);
            message_board.AddTimedMessage ("RESOURCE FOUND: " + path + "\n");
            if (path.endsWith (".php")) {
               String ExecStr = new String ("php\\php.bat ");
               ExecStr = ExecStr.concat (path);
               message_board.AddTimedMessage ("EXECUTING: " + ExecStr + "...\n");
                  try {
                  Process ppp = Runtime.getRuntime ().exec (ExecStr);
                  // Waiting for the process termination...
                  try {
                     ppp.waitFor ();
                  }
                  catch (InterruptedException e) {
                  }
                  try {
                     f = new FileInputStream ("C:\\php.out");
                     total = f.available ();
                     bbuffer = new byte [total];
                     f.read (bbuffer);
                     f.close ();
                     // Writing data...
                     message_board.AddTimedMessage ("DONE.\n");
                     WriteData ("HTTP/1.1 200 OK\n");
                     FlushOutputBuffer ();
                     WriteByteData (bbuffer);
                     File fff = new File ("C:\\php.out");
                     fff.delete ();
                  }
                  catch (IOException e) {
                     message_board.AddMessage ("(!) PHP output file open error: " + e.getMessage () + "\n");
                     WriteData ("<H1>C:\\php.out is not found</H1>\n");
                  }
               }
               catch (IOException e) {
                  message_board.AddMessage ("(!) Exec exception: " + e.getMessage () + "\n");
                  WriteData ("<H1>PHP script execution error</H1>\n");
               }
            }
            else {
               // Sending file as it is...
               try {
                  f = new FileInputStream (path);
                  total = f.available ();
                  bbuffer = new byte [total];
                  f.read (bbuffer);
                  f.close ();
                  WriteByteData (bbuffer);
               }
               catch (IOException e) {
                  WriteData ("<H1>Server internal error</H1>");
                  message_board.AddMessage ("(!) IO exception: " + e.getMessage () + "\n");
               }
            } // if .php file requested
         } // if esle variables
      } // if GET
      
      if (input_buffer.startsWith ("POST ")) {
         message_board.AddMessage ("* POST\n");
         message_board.AddMessage ("* POST requests are not implemented. Use GET instead.\n");
         WriteData ("<h3>POST is not implemented. Use GET instead...</h3>");
      }
   }
 
   public void run () {
      //message_board.AddTimedMessage ("Starting client communicator thread...\n");
      try {
         input_stream = sock.getInputStream ();
         output_stream = sock.getOutputStream ();
      }
      catch (IOException e) {
         message_board.AddMessage ("(!) Socket input/output stream exception: " + e.getMessage () + "\n");
         status = false;
      }
      input_buffer = new String ("");
      output_buffer = new String ("");
      while (status) {
         ReadData ();
         //WriteData (input_buffer);
         if (CheckHTTPRequest ()) {
            // Show the entire request
            //message_board.AddMessage (input_buffer);
            ParseHTTPRequest ();
            ClearInputBuffer ();
            //WriteData ("Hello!");
            FlushOutputBuffer ();
            status = false;
         }
      }
      terminate ();
   }
   
   public void terminate () {
      message_board.AddTimedMessage ("Shutting down client communicator thread...\n");
      try {
         input_stream.close ();
         output_stream.close ();
         sock.close ();
      }
      catch (IOException e) {
         
      }
      status = false;
   }
public void getDataForm() throws IOException{
	   final String encodedData = URLEncoder.encode( "mode=rank&group=10&q=", "Windows-1251" ) + "Http+POST";


       HttpURLConnection connection = (HttpURLConnection) new URL("http://gzip.rsdn.ru/search/").openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));

      connection.setDoOutput(true);
      final OutputStream outputStream = connection.getOutputStream();
      try {
          outputStream.write(encodedData.getBytes());
      } finally{
          outputStream.close();
      }

      System.out.println(connection.getResponseCode() + ":" + connection.getResponseMessage());


      final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "Windows-1251"));
      try {
          for (String line  = reader.readLine(); line  != null; line  = reader.readLine()) {
              System.out.println(line);
          }
      } finally {
          try {
			reader.close();
		} catch (IOException e) {}
      }
}
}

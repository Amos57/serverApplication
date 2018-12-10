package com.amos.server.php;

/***********************************************************
*                    JAVA WEB SERVER                       *
*                                                          *
* File: MessageBoard.java                                  *
* Description: Manages server console messaging            *
* Last modified: 10.08.2001                                *
***********************************************************/

import java.awt.TextArea;
import java.util.Date;

public class MessageBoard extends TextArea {
   
   public MessageBoard (String greeting) {
      super (greeting);
   }
   
   public void AddMessage (String message) {
      insert (message, 0);
   }
   
   public void AddTimedMessage (String message) {
      Date now = new Date ();
      int h = now.getHours ();
      int m = now.getMinutes ();
      int s = now.getSeconds ();
      String t = new String ("[");
      if (h < 10) t = t.concat ("0");
      t = t.concat ((new Integer (h)).toString ());
      t = t.concat (":");
      if (m < 10) t = t.concat ("0");
      t = t.concat ((new Integer (m)).toString ());
      t = t.concat (":");
      if (s < 10) t = t.concat ("0");
      t = t.concat ((new Integer (s)).toString ());
      t = t.concat ("] ");
      t = t.concat (message);
      insert (t, 0);
   }
   
}

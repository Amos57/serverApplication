package com.amos.main;

import java.io.PrintWriter;

public interface HttpRespons {

	
	
	PrintWriter getWriter();
	void senrRedirect(String path);
	void addHeader(String name,String value);
}

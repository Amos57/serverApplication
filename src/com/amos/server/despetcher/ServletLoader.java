package com.amos.server.despetcher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.amos.server.annotation.HttpServet;
import com.amos.server.data.Logger;


public class ServletLoader {

	public static final String PACKAGE="com.amos.server.servlets.";
	private  Map<String,HttpServet> servlets= new HashMap<>();
	private  Logger logger=Logger.getLogger(ServletLoader.class);
	private static ServletLoader servletLoader;
	
		private ServletLoader(){
		File classes= new File("src/com/amos/server/servlets");
		File[] arrClasses=classes.listFiles();
		logger.debug("��������� ������ "+arrClasses.length);
		for(File file : arrClasses){
			String className=file.getName().substring(0,file.getName().indexOf("."));
			Class<?> servlet = null;
			Object o = null;
			try {
				 servlet= Class.forName(PACKAGE+className);
			} catch (ClassNotFoundException e) {}
			try {
				 o=servlet.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {}
			if(o instanceof HttpServet && o!=null){
				HttpServet hServet=(HttpServet) o;
				servlets.put(hServet.getUrl(),hServet );
				
			}
		}
		logger.debug("�������� ������� "+servlets);
	}
	public static ServletLoader getInstance(){
		if(servletLoader==null)
			servletLoader= new ServletLoader();
		return servletLoader;
	}
	Map<String,HttpServet> getServlets(){
		return servlets;
	}
	
	HttpServet getServlet(String key){
		return servlets.get(key);
	}
}

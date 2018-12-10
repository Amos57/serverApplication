package com.amos.server.data;

public class Logger {

	
	private static Logger logger;
	private  String name;
	private Logger(Class clas){
		name=clas.getName();
	}
	
	public  void debug(Object row){
		String r="INFO- "+name+": "+row;
		System.out.println(r);
		
	}
	public  void error(Object row){
		String r="ERROR: "+name+row;
		System.err.println(r);
		
	}
	
	public static Logger getLogger(Class clas){
		return new Logger(clas);
	}
}

package com.amos.server.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import com.amos.server.web.Request;
import com.amos.server.web.Response;

public abstract class HttpServet {

	private String url;
	protected HttpServet() {
		
		Annotation annotation=this.getClass().getAnnotation(Servlet.class);
		if(!Objects.isNull(annotation)){
			try {
			
				Method m=annotation.getClass().getMethod("url");
			url=	(String) m.invoke(annotation);
			} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();
			} catch (IllegalAccessException e) {e.printStackTrace();
			} catch (IllegalArgumentException e) {e.printStackTrace();
			} catch (InvocationTargetException e) {}
		}
	}
	
	
    public abstract String toString();
	
    
    public void doGet(Request request,Response response){
    }
    
    
    public void doPost(Request request,Response response){
    }
    
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}

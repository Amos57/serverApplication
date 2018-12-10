package com.amos.server.html;

public class DivElement extends AbstractHTMLElement{

	
	private String value="";
	private String title ;
	
	
	@Override
	public String toString() {
		return "<div>"+value+"</div>";
	}

}

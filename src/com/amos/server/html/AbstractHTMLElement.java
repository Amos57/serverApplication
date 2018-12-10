package com.amos.server.html;

 abstract class AbstractHTMLElement {

	private String classname;
	private String id;
	private String style;
	private String onload;
	protected AbstractHTMLElement[] elements;
	
	public AbstractHTMLElement() {
		
	}
	
	public AbstractHTMLElement(AbstractHTMLElement...abstractHTMLElements){
		this.elements=abstractHTMLElements;
	}
	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOnload() {
		return onload;
	}

	public void setOnload(String onload) {
		this.onload = onload;
	}

	public abstract String toString();
}

package com.amos.server.servlets;

import java.nio.charset.StandardCharsets;

import com.amos.server.annotation.HttpServet;
import com.amos.server.annotation.JavaScript;
import com.amos.server.annotation.Servlet;
import com.amos.server.web.Request;
import com.amos.server.web.Response;


@Servlet(url="main")
public class MainPage extends HttpServet{

	private String namepage;
	@Override
	public void doGet(Request request, Response response) {
		setNamepage("Main page");
		response.setEncoding(StandardCharsets.UTF_8);
		response.getWriter().println(toString());
	}
	
	@Override
	public String toString() {
		
		return "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "<meta content=\"text/html; charset=utf-8\">"
				+ "<meta http-equiv=\'X-RU-Compartible\' content=\"ie=ebge\">"
				+ "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js\"></script>"
				+ "<title>"+namepage
				+ "</title>"
				+ "<link  type=\"text/css\"  rel=\'stylesheet\' href=\'../css/main.css\'>"
				+ "<script type=\"text/javascript\" src=\"js/data.js\"></script>"
				+ "</head>"
				+ "<body>"
				+ "<header>"
				+ "<div id=\"logo\" onclick=\"slowScroll(\'#top\')\">"
				+ "<span>itProger</span>"
				+ "</div>"
				+ "<div id=\"aboute\">"
				+ "<a href=\"#\" title=\"�����������\" onclick=\"slowScroll(\'#main\')\">�����������</a>"
				+ "<a href=\"#\" title=\"������������\" onclick=\"slowScroll(\'#main\')\">������������</a>"
				+ "<a href=\"#\" title=\"��������\" onclick=\"slowScroll(\'#main\')\">��������</a>"
				+ "<a href=\"#\" title=\"������\" onclick=\"slowScroll(\'#main\')\">FAQ</a>"
				+ "</div>"
				+ "</header>"
				+ "<div id=\"top\">"
				+ "<h1>���������������</h1>"
				+ "<h3>��� ����� �����</h3>"
				+ "</div>"
				+ "<div id=\"main\">"
				+ "<div class=\'intro\'>"
				+ "<h2>���� ������ ������� ���!</h2>"
				+ "<span>�� ����� ������</span>"
				+ "</div>"
				+ "<div id=\"text\">"
				+ "<span>�� ����� ������</span>"
				+ "</div>"
				+ "</div>"
				+ "<div id=\"overwiew\">"
				+ "<h2>������������</h2>"
				+ "<h4>� ���� �����</h4>"
				+ "<div class=\"img\">"
				+ "<img src=\"https://itproger.com/img/courses/1532975967.jpg\" alt\"\">"
				+ "<span>�������� java ��� ����������</span>"
				+ "</div>"
				+ "<div class=\"img\">"
				+ "<img src=\"https://itproger.com/img/courses/1530341233.jpg\" alt\"\">"
				+ "<span>�������� PhotoShop ��� ����������</span>"
				+ "</div>"
				+ "<div class=\"img\">"
				+ "<img src=\"https://itproger.com/img/courses/1523436361.jpg\" alt\"\">"
				+ "<span>����� swift ��� ����������</span>"
				+ "</div>"
				+ "<div class=\"img\">"
				+ "<img src=\"https://itproger.com/img/courses/1518607198.jpg\" alt\"\">"
				+ "<span>�������� ���� �� Pyton 3 c PyGame</span>"
				+ "</div>"
				+ "</div>"
				+ "<div id=\"contacts\">"
				+ "<center><h5>�������� �����</h5></center>"
				+ "<form id=\"form_input\" action=\"\" method=\"POST\">"
				+ "<label for=\"name\">��� <span>*</span></label><br>"
				+ "<input type=\"text\" placeholder=\"������� ���\" name=\"name\" id=\"name\"><br>"
				+ "<label for=\"email\">���� ����� <span>*</span></label><br>"
				+ "<input type=\"text\" placeholder=\"������� email\" name=\"email\" id=\"email\"><br>"
				+ "<label for=\"message\">��������� <span>*</span></label><br>"
				+ "<textarea placeholder=\"������� ���� ���������\" name=\"message\" id=\"message\"></textarea><br>"
				+ "<div id=\"mess_send\" class=\"btn\">Send</div>"
				+ "</form>"
				+ "</div>"
				+ "</body>"
				+ "</html>";
	}


	public String getNamepage() {
		return namepage;
	}


	public void setNamepage(String namepage) {
		this.namepage = namepage;
	}
     @JavaScript
	private void functions(){
    	/*
    	 
    	test function(){
    	console.log("hello from java class MainPage.class");
    	}
    	 */ 
     }
}

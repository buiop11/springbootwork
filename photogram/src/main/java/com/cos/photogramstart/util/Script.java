package com.cos.photogramstart.util;

public class Script {
	
	
	// 뒤 돌아가기 Javascript - 전역변수 static
	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+ msg +"');");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}

}

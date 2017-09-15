package com.sep.ballMatch.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtils {
	
	private final static String start = "<<<< LET THE SHOW BEGIN >>>>";
	
	private final static String end = "<<<< IT IS OVER >>>>";

	private final static String error = "<E~>";
	
	private final static String info = "<I~>";
	
	public final static String S = "start";
	
	public final static String EN = "end";
	
	public final static String ER = "error";
	
	public final static String I = "info";

	public static String getMutiSymbols(int i, String symbol) {
		StringBuffer result = new StringBuffer();
		if (i < 2)return symbol;
		int index = 0;
		while (index < i) {
			result.append(symbol);
			index = index + 1;
		}
		return result.toString();
	}

	public static String getExceptionToString(Exception e){
		StringWriter stringWriter= new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}
	
	public static String getThrowableToString(Throwable e){
		StringWriter stringWriter= new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}
	
	public static String process(String action , String context){
		if (S.equalsIgnoreCase(action)){
			return start + context;
		}else if (EN.equalsIgnoreCase(action)){
			return end + context;
		}else if (ER.equalsIgnoreCase(action)){
			return error + context;
		}else if (I.equalsIgnoreCase(action)){
			return info + context;
		}
		return context;
	}
}

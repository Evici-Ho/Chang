package com.dl.middleware.utils;

public class DLException extends Exception{
	public DLException() {
		super();
	}
	
	public DLException(String msg){
		super(msg);
	}
	
	public DLException(Exception e){
		super(e);
	}
	
	public String className(){
		return this.getCause().getClass().getName();
	}
	
	public String errorMsg(){
		StringBuffer str = new StringBuffer();
		StackTraceElement [] errorMsg = this.getStackTrace();
		str.append(this.toString() +" ** ");
		for(StackTraceElement s: errorMsg){
			str.append("--"+s.getMethodName()+" : "+s.toString() +";");
		}
		return str.toString();
	}
}

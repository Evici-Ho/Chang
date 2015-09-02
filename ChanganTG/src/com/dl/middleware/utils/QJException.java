package com.dl.middleware.utils;

import net.sf.json.JSON;
import net.sf.json.JSONObject;



public class QJException extends DLException{
	
	private String connectException = "{\"results\":{\"code\":\"ETS-Exception404\",\"message\":\"网络连接异常,请稍后再试\"}}";
	private String systemException = "{\"results\":{\"code\":\"ETS-Exception500\",\"message\":\"系统异常"+className()+"\"}}";
	
	public QJException() {
		super();
	}
	
	public QJException(String msg){
		super(msg);
	}
	
	public QJException(Exception e){
		super(e);
	}

	public JSONObject getConnectException() {
		return (JSONObject) BaseJSONUtils.objectToJson(connectException);
	}

	public JSONObject getSystemException() {
		return (JSONObject) BaseJSONUtils.objectToJson(systemException);
	}


}

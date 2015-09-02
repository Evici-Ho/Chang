package com.dl.middleware.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {
	
	public static boolean isContainsChinese(String str)
	{
		Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())    {
			flg = true;
		}
		return flg;
	}
	
	public static String openUrl(String urlStr,Map<String,Object> map) throws Exception {
		if(map!=null){
			String param = "?";
			for(String key: map.keySet()){
				if(isContainsChinese(map.get(key)+"")){
					param +=key+"="+URLEncoder.encode((String)map.get(key),"UTF-8")+"&";
				}else{
					param +=key+"="+map.get(key)+"&";
				}
			}
			urlStr =urlStr + param.substring(0, param.length()-1);
		}
		URL url =  new URL(urlStr);
		URLConnection connection = url.openConnection();
		connection.setReadTimeout(20*1000);
		connection.setConnectTimeout(20*1000);
		InputStream in = connection.getInputStream();  
		ByteArrayOutputStream outstream=new ByteArrayOutputStream();  
		byte[] buffer=new byte[1024];  
		int len=-1;  
		while((len=in.read(buffer)) !=-1){  
		     outstream.write(buffer, 0, len);  
		} 
		String result = new String(outstream.toByteArray(),"utf-8");
		outstream.close();  
		return result;
	}
	
	public static String openUrl(String urlStr,Map<String,Object> map,String charsetName) throws Exception {
		if(map!=null){
			String param = "?";
			for(String key: map.keySet()){
				param +=key+"="+map.get(key)+"&";
			}
			urlStr =urlStr + param.substring(0, param.length()-1);
		}
		System.out.println(urlStr);
		URL url =  new URL(urlStr);
		URLConnection connection = url.openConnection();
		String sCurrentLine;  
		String sTotalString;  
		sCurrentLine = "";  
		sTotalString = "";  
		InputStream l_urlStream;  
		l_urlStream = connection.getInputStream();  
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream,charsetName));  
		while ((sCurrentLine = l_reader.readLine()) != null) {  
			sTotalString += sCurrentLine;  
		}  
		return sTotalString;
	}
	
	public static void decodeMap(Map<String,Object> map){
		if(map!=null){
			Iterator<Entry<String,Object>> it=map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,Object> e=it.next();
				try {
					if(e.getValue()!=null){
						map.put(e.getKey(), URLDecoder.decode(e.getValue().toString(), "utf-8"));
					}
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static String getRemoteData(String url, String param, String encoding)
			throws Exception, IOException {
		System.out.println(url);
		HttpURLConnection con = (HttpURLConnection) new URL(url)
				.openConnection();
		con.setConnectTimeout(10000);
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		byte[] entity = param.getBytes();
		con.setRequestProperty("Content-Length", String.valueOf(entity.length));// entity为要传输的数据格式为
																				// title=hello&time=20//可以对该数据编码
		OutputStream outStream = con.getOutputStream();
		outStream.write(entity);
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), encoding));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		con.disconnect();
		return sb.toString();
	}
	
	public static void main(String []arg) throws Exception{
		//System.out.println(openUrl("http://localhost:8080/DLMiddleware/account/opentradeacco.action?capitalmode=4&bankacco=6222023602053606224&customerappellation=%E5%91%A8%E4%BC%9F%E5%81%A5&appkey=J7zqYf&appsecret=2ABFny&appversion=1.2.0&channel=NETNO_IOS_WALLET&idno=440181199101287511&market=AppStore&accesstoken=cac17f418908465386104789d9491f72&sign=aq6Zvq", null));
		String a="烦烦烦aaaa";
		System.out.println(URLDecoder.decode(a,"utf-8"));
		System.out.println(URLDecoder.decode(a,"utf-8"));
		System.out.println(URLDecoder.decode(a,"utf-8"));
	}
}

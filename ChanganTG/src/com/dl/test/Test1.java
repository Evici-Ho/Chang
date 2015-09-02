package com.dl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dl.wechat.java.filter.MessageUtil;
import com.dl.wechat.java.message.accept.TextMessageAccept;
import com.dl.wechat.java.message.reply.BaseMessageReply;
import com.dl.wechat.java.message.reply.TextMessageReply;
import com.thoughtworks.xstream.XStream;

public class Test1 {
	
	public static XStream xs = new XStream();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*TextMessageReply textMessageReply = null;
		textMessageReply = new TextMessageReply();
		textMessageReply.setToUserName("aa");
		textMessageReply.setFromUserName("bb");
		textMessageReply.setCreateTime(new Date().getTime());
		textMessageReply.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessageReply.setFuncFlag(0);
		textMessageReply.setContent("send msg");
		String respMessage = MessageUtil.textMessageToXml(textMessageReply);
		System.out.println(respMessage);*/
		
		String xml = "<xml>"
				+ "<ToUserName><![CDATA[aa]]></ToUserName>"
				+ "<FromUserName><![CDATA[bb]]></FromUserName>"
				+ "<CreateTime><![CDATA[1440743862433]]></CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<FuncFlag><![CDATA[0]]></FuncFlag>"
				+ "<Content><![CDATA[send msg]]></Content>"
				+ "</xml>";
		TextMessageReply obj = new TextMessageReply();
		obj.setContent("aa");
		System.out.println(obj.getContent());
		
		TextMessageReply obj1 = (TextMessageReply)xmlToBean(obj);
		System.out.println(obj1.getToUserName());
		
		/*Field field1 = TextMessageReply.class.getDeclaredField("Content");
		field1.setAccessible(true);
		field1.set(obj, "bb");
		System.out.println(obj.getContent());*/
		
		/*Class<?> clazz = Class.forName(messageClazz);
		Constructor<?> constructor = clazz.getConstructor();
		Object messageObj = constructor.newInstance();*/
		
		System.out.println("*********************");
		TextMessageReply obj2 = (TextMessageReply)parseXml("com.dl.wechat.java.message.reply.TextMessageReply");
		System.out.println("AAA" + obj2.getContent());
	}
	
	public static Object parseXml(String classname) throws Exception {
		
		Class<?> clazz = Class.forName(classname);
		Constructor<?> constructor = clazz.getConstructor();
		Object messageObj = constructor.newInstance();
		System.out.println(messageObj.getClass().getName());
		
        // 将解析结果存储在HashMap中
        //Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        //InputStream inputStream = request.getInputStream();
        InputStream inputStream = new FileInputStream(new File("E:\\b.txt"));
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList){
        	//Field field = messageObj.getClass().getField(e.getName());
    		//field.setAccessible(true);
        	setBean(messageObj, e.getName(), e.getText());

        }

        // 释放资源
        inputStream.close();
        inputStream = null;
        
        return messageObj;
    }
	
	public static void setBean(Object messageObj, String name, String value)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
		Field field = messageObj.getClass().getField(name);
		String fieldType = field.getType().getName(); // 获取属性的类型
		String fieldName = field.getName();
		Method m = messageObj.getClass().getMethod("set" + fieldName, Class.forName(fieldType));

		if (fieldType.equals("java.lang.String")) {
			m.invoke(messageObj, value);
		}
		if (fieldType.matches("java.lang.Integer")) {
			m.invoke(messageObj, Integer.parseInt(value));
		}
		if (fieldType.matches("java.lang.Long")) {
			m.invoke(messageObj, Long.parseLong(value));
		}
	}
	
	public static Object xmlToBean(Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if(obj.getClass().getField("ToUserName") != null){
			Field field1 = obj.getClass().getField("ToUserName");
			field1.setAccessible(true);
			field1.set(obj, "bb");
		}
		//System.out.println(obj.getContent());
		return obj;
	}
	
	public static void reflect(Object obj) {  
        if (obj == null)  
            return;  
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] types1={"int","java.lang.String","boolean","char","float","double","long","short","byte"};  
        String[] types2={"Integer","java.lang.String","java.lang.Boolean","java.lang.Character","java.lang.Float","java.lang.Double","java.lang.Long","java.lang.Short","java.lang.Byte"};  
        for (int j = 0; j < fields.length; j++) {  
            fields[j].setAccessible(true);  
            // 字段名  
          System.out.print(fields[j].getName() + ":");  
            // 字段值  
            for(int i=0;i<types1.length;i++){  
                if(fields[j].getType().getName()  
                        .equalsIgnoreCase(types1[i])|| fields[j].getType().getName().equalsIgnoreCase(types2[i])){  
                    try {  
                        System.out.print(fields[j].get(obj)+"     ");  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }   
                }  
            }  
        }  
    } 
	
	public static Object xmlToObject(XStream xs,String xml,Class<?> c){
		xs.alias("xml", c);
		return xs.fromXML(xml);
	}
	
	public static Object xmlToObject(String xml,Class<?> c){
		xs.alias("xml", c);
		return xs.fromXML(xml);
	}

	public static void sort(String a[]) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j].compareTo(a[i]) < 0) {
					String temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}

}

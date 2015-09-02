package com.dl.wechat.java.message.handler;

import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dl.wechat.java.message.accept.TextMessageAccept;
import com.dl.wechat.java.message.reply.TextMessageReply;
//import com.javen.course.message.resp.Article;
//import com.javen.course.message.resp.MusicMessage;
//import com.javen.course.message.resp.NewsMessage;
//import com.javen.course.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类
 * @author Javen
 * @Email zyw205@gmail.com
 */
public class BaseMessageService {

    /**
     * 解析微信发来的请求（XML）
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object parseXml(HttpServletRequest request, String classname) throws Exception {
    	
    	Class<?> clazz = Class.forName(classname);
		Constructor<?> constructor = clazz.getConstructor();
		Object messageObj = constructor.newInstance();
        // 将解析结果存储在HashMap中
        //Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList){
        	setBean(messageObj, e.getName(), e.getText());
            //map.put(e.getName(), e.getText());
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

    /**
     * 文本消息对象转换成xml
     * 
     * @param textMessage 文本消息对象   XStream是一个Java对象和XML相互转换的工具
     * @return xml
     */
    
    public static String textMessageToXml(TextMessageReply textMessageReply) {
        xstream.alias("xml", textMessageReply.getClass());
        return xstream.toXML(textMessageReply);
    }

    /*
    *//**
     * 音乐消息对象转换成xml
     * 
     * @param musicMessage 音乐消息对象
     * @return xml
     *//*
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    *//**
     * 图文消息对象转换成xml
     * 
     * @param newsMessage 图文消息对象
     * @return xml
     *//*
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }*/

    /**
     * 扩展xstream，使其支持CDATA块
     * 
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
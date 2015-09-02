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
 * ��Ϣ������
 * @author Javen
 * @Email zyw205@gmail.com
 */
public class BaseMessageService {

    /**
     * ����΢�ŷ���������XML��
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
        // ����������洢��HashMap��
        //Map<String, String> map = new HashMap<String, String>();

        // ��request��ȡ��������
        InputStream inputStream = request.getInputStream();
        // ��ȡ������
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // �õ�xml��Ԫ��
        Element root = document.getRootElement();
        // �õ���Ԫ�ص������ӽڵ�
        List<Element> elementList = root.elements();
        // ���������ӽڵ�
        for (Element e : elementList){
        	setBean(messageObj, e.getName(), e.getText());
            //map.put(e.getName(), e.getText());
        }

        // �ͷ���Դ
        inputStream.close();
        inputStream = null;

        return messageObj;
    }
    
    public static void setBean(Object messageObj, String name, String value)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
		Field field = messageObj.getClass().getField(name);
		String fieldType = field.getType().getName(); // ��ȡ���Ե�����
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
     * �ı���Ϣ����ת����xml
     * 
     * @param textMessage �ı���Ϣ����   XStream��һ��Java�����XML�໥ת���Ĺ���
     * @return xml
     */
    
    public static String textMessageToXml(TextMessageReply textMessageReply) {
        xstream.alias("xml", textMessageReply.getClass());
        return xstream.toXML(textMessageReply);
    }

    /*
    *//**
     * ������Ϣ����ת����xml
     * 
     * @param musicMessage ������Ϣ����
     * @return xml
     *//*
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    *//**
     * ͼ����Ϣ����ת����xml
     * 
     * @param newsMessage ͼ����Ϣ����
     * @return xml
     *//*
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }*/

    /**
     * ��չxstream��ʹ��֧��CDATA��
     * 
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ������xml�ڵ��ת��������CDATA���
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
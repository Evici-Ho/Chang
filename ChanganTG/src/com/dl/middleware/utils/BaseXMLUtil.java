package com.dl.middleware.utils;

import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat; 
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class BaseXMLUtil {

	protected static String PREFIX_CDATA = "<![CDATA[";
	protected static String SUFFIX_CDATA = "]]>";
	public static XStream xs = new XStream();
	/**
	 * 将xml转换成对�?
	 * @param xml
	 * @return
	 */
	public static Object xmlToObject(String xml){
		return xs.fromXML(xml);
	}
	/**
	 * 使用自定义Xstream对象转换
	 * @param xs
	 * @param xml
	 * @return
	 */
	public static Object xmlToObject(XStream xs,String xml){
		return xs.fromXML(xml);
	}
	/**
	 * 将xml转换为xml并将根节点名字替换成xml
	 * @param xs
	 * @param xml
	 * @param c
	 * @return
	 */
	public static Object xmlToObject(String xml,Class<?> c){
		xs.alias("xml", c);
		return xs.fromXML(xml);
	}
	public static Object xmlToObject(XStream xs,String xml,Class<?> c){
		xs.alias("xml", c);
		return xs.fromXML(xml);
	}
	/**
	 * 将对象转成xml
	 * @param obj
	 * @return
	 */
	public static String objectToXml(Object obj){
		return xs.toXML(obj);
	}
	/**
	 * 用自定义XStream，将对象转成xml
	 * @param xs
	 * @param obj
	 * @return
	 */
	public static String objectToXml(XStream xs,Object obj){
		return xs.toXML(obj);
	}
	/**
	 * 将对象转成xml并把根节点从对象的class替换成xml
	 * @param xs
	 * @param xml
	 * @param c
	 * @return
	 */
	public static String objectToXml(Object obj,Class<?> c){
		xs.alias("xml", c);
		return xs.toXML(obj);
	}
	public static String objectToXml(XStream xs,Object obj,Class<?> c){
		xs.alias("xml", c);
		return xs.toXML(obj);
	}
	/**
	 * 对象转成xml,根节点改成xml，并配置不需要添加CDTA的属�?
	 * @param xs
	 * @param obj
	 * @param c
	 * @param paramList 不需要添加CDTA的属性list
	 * @return
	 */
	public static String objectToXmlExCDATA(XStream xs,Object obj,Class<?> c,List<String> paramList){
		int index;
		xs.alias("xml", c);
		String xml = xs.toXML(obj);
		for(String param : paramList){
			index = xml.indexOf(param);
			if(index != -1){
				xml = xml.substring(0, index+param.length()+1)+xml.substring(index+param.length()+10);
			}
			index = xml.indexOf("/"+param);
			if(index != -1){
				xml = xml.substring(0, index-4)+xml.substring(index-1);
			}
		}
		//logger.info("输出信息:\n["+xml+"]");
		return xml;
	}
	/**
	 * 对象转成xml,根节点改成xml，并配置不需要添加CDTA的属性，并配置需要修改名字的节点
	 * @param xs
	 * @param obj
	 * @param c
	 * @param paramList
	 * @param parmMap Map的key对应输出的xml节点，value对应是需要修改的Class
	 * @return
	 */
	public static String objectToXmlExCDATACE(XStream xs,Object obj,Class<?> c,List<String> paramList,Map<String,Class<?>> parmMap){
		int index;
		xs.alias("xml", c);
		Iterator iter = parmMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			Class<?> value = (Class<?>)entry.getValue();
			xs.alias(key, value);
		}
		String xml = xs.toXML(obj);
		for(String param : paramList){
			index = xml.indexOf(param);
			if(index != -1){
				xml = xml.substring(0, index+param.length()+1)+xml.substring(index+param.length()+10);
			}
			index = xml.indexOf("/"+param);
			if(index != -1){
				xml = xml.substring(0, index-4)+xml.substring(index-1);
			}
		}
		//logger.info("输出信息:\n["+xml+"]");
		return xml;
	}

	public static String objectToXml(XStream xs,Object obj,Map<String,Class<?>> parmMap){
		Iterator iter = parmMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			Class<?> value = (Class<?>)entry.getValue();
			xs.alias(key, value);
		}
		String xml = xs.toXML(obj);
		return xml;
	}

	/*public static String weixinObjectToXMl(Object obj){
		XStream xs = BaseXMLUtil.getXStreamWithCDATA();
		List<String> paramList = new ArrayList<String>();
		paramList.add("CreateTime");
		if(obj.getClass().equals(NewsMessageRes.class)){
			Map<String,Class<?>> map = new HashMap<String,Class<?>>();
			map.put("item", Articles.class);
			return objectToXmlExCDATACE(xs,obj,obj.getClass(),paramList,map);
		}else if(obj.getClass().equals(TextMessageRes.class)){
			return objectToXmlExCDATA(xs, obj, obj.getClass(), paramList);
		}else if(obj.getClass().equals(ImageMessageRes.class)){
			Map<String,Class<?>> map = new HashMap<String,Class<?>>();
			map.put("Image", Image.class);
			return objectToXmlExCDATACE(xs, obj, obj.getClass(), paramList,map);
		}else if(obj.getClass().equals(VoiceMessageRes.class)){
			Map<String,Class<?>> map = new HashMap<String,Class<?>>();
			map.put("Voice", Voice.class);
			return objectToXmlExCDATA(xs, obj, obj.getClass(), paramList);
		}else if(obj.getClass().equals(VideoMessageRes.class)){
			Map<String,Class<?>> map = new HashMap<String,Class<?>>();
			map.put("Video", Video.class);
			return objectToXmlExCDATA(xs, obj, obj.getClass(), paramList);
		}else if(obj.getClass().equals(MusicMessageRes.class)){
			Map<String,Class<?>> map = new HashMap<String,Class<?>>();
			map.put("Music", Music.class);
			return objectToXmlExCDATA(xs, obj, obj.getClass(), paramList);
		}else{
			return null;
		}
	}*/

	public static Object xmlToObject(XStream xs,String xml,Map<String,Class<?>> parmMap){
		Iterator iter = parmMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			Class<?> value = (Class<?>)entry.getValue();
			xs.alias(key, value);
		}
		return xs.fromXML(xml);
	}


	/**
	 * 创建�?个安全的XStream对象，对于多余的属�?�会自动排除并装配到bean
	 * @return
	 */
	public static XStream getSecurityXStream(){
		XStream xs = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(Class definedIn,
							String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		return xs;
	}
	/**
	 * 初始化XStream 可支持某�?字段可以加入CDATA标签 如果�?要某�?字段使用原文
	 * 就需要在String类型的text的头加上"<![CDATA["和结尾处加上"]]>"标签�? 以供XStream输出时进行识�?
	 * 
	 * @param isAddCDATA
	 *            是否支持CDATA标签
	 * @return
	 */
	public static XStream getXStreamWithCDATA() {
		XStream xstream = new XStream(new XppDriver() {
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					protected void writeText(QuickWriter writer, String text) {
						if (!text.startsWith(PREFIX_CDATA)) {
							text = PREFIX_CDATA + text + SUFFIX_CDATA;
						}
						writer.write(text);
					}
				};
			};
		});
		return xstream;
	}

	public static String doc2String(Document document,String charset) {  
		String s = "";  
		if(charset==null || charset.equals("")){
			charset = "UTF-8";
		}
		try {  
			// 使用输出流来进行转化  
			ByteArrayOutputStream out = new ByteArrayOutputStream();  
			// 使用UTF-8编码  
			//OutputFormat format = new OutputFormat("", true, "UTF-8");  
			//XMLWriter writer = new XMLWriter(out,format);  

			OutputFormat format = new OutputFormat("", false, charset);  
			XMLWriter writer = new XMLWriter(out,format);  
			writer.write(document);  
			s = out.toString("UTF-8").replaceAll("(?s)<\\?xml.*?\\?>","").replaceAll("\n","");
			//s = document.asXML();
		} catch (Exception ex) {  
			ex.printStackTrace();  
		}  
		return s;  
	}  

	public static String maptoXml(String rootName,Map<String,Object> map) {  
		Document document = DocumentHelper.createDocument();  
		Element nodeElement = document.addElement(rootName);  
		if(map!=null){
			for (String obj : map.keySet()) {  
				if(map.get(obj)!=null){
					Element keyElement = nodeElement.addElement(obj);
					keyElement.setText(String.valueOf(map.get(obj)));  
				}
			}  
		}
		return doc2String(document,"UTF-8");  
	}  

	/** 
	 * list转xml
	 * @param list 
	 * @return 
	 */  
	public static String listtoXml(List list) throws Exception {  
		Document document = DocumentHelper.createDocument();  
		Element nodesElement = document.addElement("nodes");  
		int i = 0;  
		for (Object o : list) {  
			Element nodeElement = nodesElement.addElement("node");  
			if (o instanceof Map) {  
				for (Object obj : ((Map) o).keySet()) {  
					Element keyElement = nodeElement.addElement("key");  
					keyElement.addAttribute("label", String.valueOf(obj));  
					keyElement.setText(String.valueOf(((Map) o).get(obj)));  
				}  
			} else {  
				Element keyElement = nodeElement.addElement("key");  
				keyElement.addAttribute("label", String.valueOf(i));  
				keyElement.setText(String.valueOf(o));  
			}  
			i++;  
		}  
		return doc2String(document,"UTF-8");  
	}  

	/** 
	 * json转xml 
	 * @param json 
	 * @return 
	 */  
	public static String jsontoXml(String json) {  
		JSONObject jobj = JSONObject.fromObject(json);
		String xml =  new XMLSerializer().write(jobj);
		return xml;
	}  

	/** 
	 * xml转map
	 * @param xml 
	 * @return 
	 */  
	public static Map xmltoMap(String xml) {  
		try {  
			Document doc = DocumentHelper.parseText(xml);  
			Map<String, Object> map = new HashMap<String, Object>(); 
	        if(doc == null) 
	            return map; 
	        Element root = doc.getRootElement(); 
	        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
	            Element e = (Element) iterator.next(); 
	            List list = e.elements(); 
	            if(list.size() > 0){ 
	                map.put(e.getName(), Dom2Map(e)); 
	            }else 
	                map.put(e.getName(), e.getText()); 
	        } 
			return map; 
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return null;  
	}  

	public static Map Dom2Map(Element e){ 
        Map map = new HashMap(); 
        List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else 
            map.put(e.getName(), e.getText()); 
        return map; 
    } 
	/** 
	 * xml转list
	 * @param xml 
	 * @return 
	 */  
	public static List xmltoList(String xml) {  
		try {  
			List<Map> list = new ArrayList<Map>();  
			Document document = DocumentHelper.parseText(xml);  
			Element nodesElement = document.getRootElement();  
			List nodes = nodesElement.elements();  
			for (Iterator its = nodes.iterator(); its.hasNext();) {  
				Element nodeElement = (Element) its.next();  
				Map map = xmltoMap(nodeElement.asXML());  
				list.add(map);  
				map = null;  
			}  
			nodes = null;  
			nodesElement = null;  
			document = null;  
			return list;  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return null;  
	}  

	/** 
	 * xml转json
	 * @param xml 
	 * @return 
	 */  
	public static String xmltoJsonString(String xml) {  
		XMLSerializer xmlSerializer = new XMLSerializer();  
		return xmlSerializer.read(xml).toString();  
	}  
	
	/** 
	 * xml转json
	 * @param xml 
	 * @return 
	 */  
	public static JSON xmltoJson(String xml) {  
		XMLSerializer xmlSerializer = new XMLSerializer();  
		return xmlSerializer.read(xml);  
	}  

	public static void main(String[] arg){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("trancode", "custBase/logIn");
		String result = maptoXml("Header",map);
		System.out.println(result);
	}
}

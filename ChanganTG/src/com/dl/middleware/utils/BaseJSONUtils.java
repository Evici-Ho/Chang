package com.dl.middleware.utils;

import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * 
*    
* 项目名称：WeiJianUtils   
* 类名称：BaseJSONUtils   
* 类描述：json操作工具�?   
* 创建人：WeiJian
* 创建时间�?2014�?8�?22�? 上午11:13:02    
* 备注�?   
* @version    1.0
*
 */
public class BaseJSONUtils {
	public static final String ROOT = "root";
	public static final String TOTAL_PROPERTY = "totalProperty";
	public static final String MSG = "msg";
	public static final String SUCCESS = "success";
	public static final String DATA = "data";
	public static JsonConfig JC=null;
	
	static{
		//String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};   
		//JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		DateJsonValueProcessor dateJsonValueProcessor =new DateJsonValueProcessor();//默认日期转换(java-->json),默认�?:yyyy-MM-dd HH:mm:ss
		JC = new JsonConfig();
		JC.registerJsonValueProcessor(Date.class,dateJsonValueProcessor);
		JC.registerJsonValueProcessor(Timestamp.class,dateJsonValueProcessor);
	}
	
	/**
	 * 转换成json信息，转换后的格式如下：
	 * <dl>
	 * <dt>当obj为布尔类型时�?</dt>
	 * <dd>{success:true/false}</dd>
	 * 
	 * <dt>当obj为字符串类型时：</dt>
	 * <dd>{msg:""}</dd>
	 * 
	 * <dt>当obj为集合类型或数组时：</dt>
	 * <dd>[{},{}..]</dd>
	 * 
	 * <dt>当obj为其他类型时�?</dt>
	 * <dd>{{}}</dd>
	 * </dl>
	 * @param  要转换的对象
	 * @param msg 提示信息，当obj的类型为Collection时无�?
	 * @param writer 将json写入到此写入流中，为null时不写入
	 * @return 转换后的json字符�?
	 */
	public static String objextToJson(Object obj ,Writer writer){
		JSON json = objectToJson(obj);
		if(writer != null){
			json.write(writer);
		}
		return json.toString();
	}
	/**
	 * @param obj 要转换的对象
	 * @param msg 提示信息，当obj的类型为Collection时无�?
	 * @return JSON
	 */
	public static JSON objectToJson(Object obj){
		if(JSONUtils.isArray(obj)){
			return JSONArray.fromObject(transferArray(obj),JC);
		}else {
			JSONObject json = new JSONObject();
			if(JSONUtils.isString(obj)){
				json.put(MSG, obj);
			}else if(JSONUtils.isBoolean(obj)){
				json.put(SUCCESS, obj);
			}else{
				json = JSONObject.fromObject(obj,JC);
			}
			return json;
		}
	}
	/**
	 * 转换成以下格�?
	 * <dl>
	 * <dd>[data:{},xx:xx,xx:xx,..]</dd>
	 * <dl>
	 * @param obj 要转换的对象
	 * @param map 另外�?要添加的json信息
	 * @return JSON
	 */
	
	public static JSON objectToJsonAddMap(Object obj,Map map){
		if(JSONUtils.isArray(obj)){
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("data", transferArray(obj));
			JSONObject jsonObject = JSONObject.fromObject(jsonMap,JC);
			jsonObject.putAll(map);
			return jsonObject;
		}else {
			JSONObject json = new JSONObject();
			if(JSONUtils.isString(obj)){
				json.put(MSG, obj);
			}else if(JSONUtils.isBoolean(obj)){
				json.put(SUCCESS, obj);
			}else{
				json.putAll(map);
				json.elementOpt("data", obj, JC);
			}
			return json;
		}
	}
	
	/**
	 * 转换成json对象并忽略一些关键字
	 * @param obj 要转换的对象
	 * @param parmList �?要忽略的条件
	 * @return JSON
	 */
	public static JSON objextToJsonExceptByName(Object obj,List<Object> parmList){
		if(JSONUtils.isArray(obj)){
			List<Object> list = transferArray(obj);
			JSONObject[] jsonObjects = new JSONObject[list.size()];
			JSONArray jsonArray = JSONArray.fromObject(transferArray(obj));
			for(int i = 0 ; i<list.size() ; i++){
				jsonObjects[i] = jsonArray.getJSONObject(i);
				for(Object key : parmList){
					jsonObjects[i].remove(key);
				}
			}
			JSONArray returnArray = new JSONArray();
			for(JSONObject jsonobj : jsonObjects)
			returnArray.add(jsonobj);
			return returnArray;
		}else {
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(obj);
			/*if(JSONUtils.isString(obj)){
				json.put(SUCCESS, false);
				json.put(MSG, obj);
			}else if(JSONUtils.isBoolean(obj)){
				json.put(SUCCESS, obj);
			}else{
				json.put(SUCCESS, true);
				if(obj instanceof JSONObjectTransfer){
					obj = ((JSONObjectTransfer)obj).toJSONObject();
				}
				json.put(DATA, obj);
			}*/
			for(Object key : parmList){
				json.remove(key);
			}
			return json;
		}
	}
	
	/**
	 * 转换成json对象并更改他的key
	 * @param obj
	 * @param parmMap
	 * @return
	 */
	public static JSON objectToJsonChangKey(Object obj,Map parmMap){
		if(JSONUtils.isArray(obj)){
			List<Object> list = transferArray(obj);
			JSONObject[] jsonObjects = new JSONObject[list.size()];
			JSONArray jsonArray = JSONArray.fromObject(transferArray(obj));
			for(int i = 0 ; i<list.size() ; i++){
				jsonObjects[i] = jsonArray.getJSONObject(i);
				Iterator iter = parmMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object jsonKey = entry.getValue();
					Object jsonValue = jsonObjects[i].get(key);
					jsonObjects[i].put(jsonKey, jsonValue);
					jsonObjects[i].remove(key);
				}
			}
			JSONArray returnArray = new JSONArray();
			returnArray.add(jsonObjects);
			return returnArray;
		}else {
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(obj);
			Iterator iter = parmMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object jsonKey = entry.getValue();
				Object jsonValue = json.get(key);
				json.put(jsonKey, jsonValue);
				json.remove(key);
			}
			return json;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> transferArray(Object arr){
		List<Object> list = new ArrayList<Object>();
		//当为数组�?
		if(arr instanceof Object[]){
			Object[] arrs = (Object[])arr;
			for(Object obj : arrs){
				if(obj instanceof JSONObjectTransfer){
					JSONObjectTransfer jt = (JSONObjectTransfer)obj;
					obj = jt.toJSONObject();
				}
				list.add(obj);
			}
		}else if(arr instanceof Collection){
			Collection cln = (Collection)arr;
			Iterator<Object> iter = cln.iterator();
			while(iter.hasNext()){
				Object obj = iter.next();
				if(obj instanceof JSONObjectTransfer){
					JSONObjectTransfer jt = (JSONObjectTransfer)obj;
					obj = jt.toJSONObject();
				}
				list.add(obj);
			}
		}
		return list;
	}
	
	/**
	 * <p>
	 * {success:true,totalProperty:99,root:[{},{}..]}
	 * </p>
	 * @param collection 数组或集合类�?
	 * @param total 总数
	 * @param writer 写入流，为null时不写入
	 * @param config JsonConfig配置
	 * @return 转换后的json字符�?
	 */
	public static String listToJson(Object collection,long total,Writer writer,JsonConfig config){
		List<Object> list = transferArray(collection);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(TOTAL_PROPERTY, total);
		jsonMap.put(ROOT, list);
		JSONObject jsonObject = config != null ? JSONObject.fromObject(jsonMap,
				config) : JSONObject.fromObject(jsonMap,JC);
		if(writer != null){
			jsonObject.write(writer);
		}
		return jsonObject.toString();
	}
	
	/**
	 * <p>
	 * {success:true,totalProperty:99,root:[{},{}..]}
	 * </p>
	 * @param collection 数组或集合类�?
	 * @param total 总数
	 * @param writer 写入流，为null时不写入
	 * @param config JsonConfig配置
	 * @return 转换后的json字符�?
	 */
	public static String listToJsonWithExt(Object collection,long total,Writer writer,Map<String,Object> ext){
		List<Object> list = transferArray(collection);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(TOTAL_PROPERTY, total);
		jsonMap.put(ROOT, list);
		if(ext != null)
			jsonMap.putAll(ext);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonMap,JC);
		if(writer != null){
			jsonObject.write(writer);
		}
		return jsonObject.toString();
	}
	
	public static JSON mapToJson(Map map){
		JSONObject json = new JSONObject();
		json.putAll(map,JC);
		return json;
	}
	
	/**
	 * description:将json字符串转为map类型
	 *
	 * @author: yingjie
	 */
	/*public static Map<String,Object> jsonStringToMap(String json){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		com.alibaba.fastjson.JSONObject jsonObject=(com.alibaba.fastjson.JSONObject)com.alibaba.fastjson.JSON.parse(json);
		Set<Map.Entry<String, Object>> set=jsonObject.entrySet();
		Iterator<Map.Entry<String, Object>> it=set.iterator();
		while(it.hasNext()){
			Entry<String,Object> entry=it.next();
			resultMap.put(entry.getKey(),entry.getValue());
		}
		return resultMap;
	}*/
}

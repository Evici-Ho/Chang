package com.dl.middleware.utils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qunji.webservice.client.call.Call;

public class WebServiceUtil {
	static Log logger = LogFactory.getLog(WebServiceUtil.class);
	
	/*public static String callMethod(String methodName, Map<String, Object> param) {
		return callMethod(Configs.WEBSERVICEURL,Configs.SOAPACTION,methodName,param);
	}

	public static String callMethod(String url, String soapaction,
			String methodName, Map<String, Object> param) {
		long startTime = System.currentTimeMillis();
		Service service = new Service();
		Call call = null;
		String result = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(soapaction, methodName)); // 璁剧疆瑕佽皟鐢ㄥ摢涓柟娉?
			Object[] object = new Object[param.size()];
			int index = 0;
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				String key = entry.getKey();
				Object o = entry.getValue();
				object[index++] = o;
				if (o instanceof Integer) {
					call.addParameter(
							new QName(soapaction, key), // 璁剧疆瑕佷紶閫掔殑鍙傛暟
							XMLType.XSD_INT,
							javax.xml.rpc.ParameterMode.IN);
				} else if (o instanceof byte[]) {
					call.addParameter(
							new QName(soapaction, key), // 璁剧疆瑕佷紶閫掔殑鍙傛暟
							XMLType.XSD_BASE64,
							javax.xml.rpc.ParameterMode.IN);
				} else{
					call.addParameter(
							new QName(soapaction, key), // 璁剧疆瑕佷紶閫掔殑鍙傛暟
							XMLType.XSD_STRING,
							javax.xml.rpc.ParameterMode.IN);
				}

			}
			call.setEncodingStyle("wsdl"); // 鍔犱簡杩欏彞鎵嶈兘浼爄nt鐨勭被鍨?
			call.setReturnType(XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("");
			result = ((String) call.invoke(object)).replaceAll(">\\s+<", "><");
		} catch (Exception e) {
			logger.info("鎺ュ彛"+methodName+"璋冪敤鍑洪敊锛?", e);
			logger.info("鎺ュ彛鍙傛暟"+param.toString());
			return Configs.ERROR_CALL;
		}
		return result;
	}*/
	public static String callMethod(String sendMessage, Map<String, Object> param) throws Exception {
		String recvMessage = "";
		try {
			Call call = new Call();
			boolean bReturn = call.remoteCall(((String) param.get("arg0")));
			if (bReturn) {
				recvMessage = call.getRecvMessage();
			}
		} catch (Exception e) {
			logger.error("出错详情：", e);
		}

		return recvMessage;
	}
	
	/*public static void check(){
		Map<String, Object> bodyParam = new HashMap<String, Object>();
		Map<String, Object> headerExt = new HashMap<String, Object>();
		String trancode = "serviceBase/ecCheckValidate";
		//0-快捷开户 1-银行卡签约 2-撤约 3-银行卡开 4-手机号唯一性开户 5-直销柜台设置密码
		if(privateMap.get("operatetype").equals("5")){
			bodyParam.put("operatetype",0);
		}else{
			bodyParam.put("operatetype",privateMap.get("operatetype"));
		}
		//当operatetype=1或2的时候必填，由客户开户或登录成功后系统返回的代表该客户的唯一标识
		bodyParam.put("custid", privateMap.get("custid"));
		//只有当operatetype=2的情况下必填，由客户银行卡绑定成功后系统返回的代表该客户银行卡信息的唯一标识
		bodyParam.put("transactionaccountid", privateMap.get("transactionaccountid"));
		bodyParam.put("bankcode",privateMap.get("bankcode"));
		//资金账号即银行卡号
		bodyParam.put("accountid", privateMap.get("accountid"));
		
		bodyParam.put("custname", privateMap.get("custname"));
		//资金结算方式 默认3
		bodyParam.put("finatype", privateMap.get("finatype"));
		//默认填902
		bodyParam.put("paychannelcode", privateMap.get("paychannelcode"));
		//非必传
		bodyParam.put("certificatetype", privateMap.get("certificatetype"));
		//非必传
		bodyParam.put("certificateno", privateMap.get("certificateno"));
		JSONObject result = QunJietInterface.call(trancode, headerExt, bodyParam);
		
		<Header>
	    <trancode>serviceBase/sendSms</trancode>
	    <sysfromcode>0666</sysfromcode>
	    <netno>999999</netno>
	    <clientipaddress>192.168.23.3</clientipaddress>
	   <clientseriousno>*************</clientseriousno>
	  </Header>
	  <Body>
	   <mobiletelno>13688882999</mobiletelno>
	  </Body>
	}*/
	
	public static JSONObject call(String trancode) {
		try {
			long start_time = System.currentTimeMillis();
			String rootStart="<Message>";
			String rootEnd="</Message>";
			String recvMessage = "";
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("trancode", "serviceBase/sendSms");
			map.put("sysfromcode", "0666");
			map.put("netno", "999999");
			map.put("clientipaddress", InetAddress.getLocalHost().getHostAddress());
			map.put("clientseriousno", RandomUtil.generateString(24));
			
			Map<String,Object> bodyParam = new HashMap<String, Object>();
			bodyParam.put("mobiletelno", "13530023822");
			
			/*if(headerExt!=null){
				map.putAll(headerExt);
			}*/
			String Header = BaseXMLUtil.maptoXml("Header",map);
			String Body = BaseXMLUtil.maptoXml("Body",bodyParam);
			recvMessage = rootStart + Header + Body + rootEnd;
			logger.info("群济接口:" + trancode + " 入参：" + recvMessage);
			String methodName="service";
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("arg0", recvMessage);
			String result = WebServiceUtil.callMethod(methodName,param);
			logger.info("群济接口:"+trancode +" 返回参数："+ result +" \n请求时间" + (System.currentTimeMillis() - start_time) +"ms");
			return (JSONObject) BaseJSONUtils.mapToJson(BaseXMLUtil.xmltoMap(result));
		} catch (Exception e) {
			QJException dle = new QJException(e);
			//String errorMsg = dle.errorMsg();
			String errorClass = dle.className();
			logger.error("系统异常",e);
			if(errorClass.contains("java.net")){
				return dle.getConnectException();
			}else{
				return dle.getSystemException();
			}
		}
	}
	
	public static void main(String[] args) {
		
		WebServiceUtil.call("serviceBase/sendSms");
	}
}

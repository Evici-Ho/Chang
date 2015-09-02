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
			call.setOperationName(new QName(soapaction, methodName)); // 设置要调用哪个方�?
			Object[] object = new Object[param.size()];
			int index = 0;
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				String key = entry.getKey();
				Object o = entry.getValue();
				object[index++] = o;
				if (o instanceof Integer) {
					call.addParameter(
							new QName(soapaction, key), // 设置要传递的参数
							XMLType.XSD_INT,
							javax.xml.rpc.ParameterMode.IN);
				} else if (o instanceof byte[]) {
					call.addParameter(
							new QName(soapaction, key), // 设置要传递的参数
							XMLType.XSD_BASE64,
							javax.xml.rpc.ParameterMode.IN);
				} else{
					call.addParameter(
							new QName(soapaction, key), // 设置要传递的参数
							XMLType.XSD_STRING,
							javax.xml.rpc.ParameterMode.IN);
				}

			}
			call.setEncodingStyle("wsdl"); // 加了这句才能传int的类�?
			call.setReturnType(XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("");
			result = ((String) call.invoke(object)).replaceAll(">\\s+<", "><");
		} catch (Exception e) {
			logger.info("接口"+methodName+"调用出错�?", e);
			logger.info("接口参数"+param.toString());
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
			logger.error("�������飺", e);
		}

		return recvMessage;
	}
	
	/*public static void check(){
		Map<String, Object> bodyParam = new HashMap<String, Object>();
		Map<String, Object> headerExt = new HashMap<String, Object>();
		String trancode = "serviceBase/ecCheckValidate";
		//0-��ݿ��� 1-���п�ǩԼ 2-��Լ 3-���п��� 4-�ֻ���Ψһ�Կ��� 5-ֱ����̨��������
		if(privateMap.get("operatetype").equals("5")){
			bodyParam.put("operatetype",0);
		}else{
			bodyParam.put("operatetype",privateMap.get("operatetype"));
		}
		//��operatetype=1��2��ʱ�����ɿͻ��������¼�ɹ���ϵͳ���صĴ���ÿͻ���Ψһ��ʶ
		bodyParam.put("custid", privateMap.get("custid"));
		//ֻ�е�operatetype=2������±���ɿͻ����п��󶨳ɹ���ϵͳ���صĴ���ÿͻ����п���Ϣ��Ψһ��ʶ
		bodyParam.put("transactionaccountid", privateMap.get("transactionaccountid"));
		bodyParam.put("bankcode",privateMap.get("bankcode"));
		//�ʽ��˺ż����п���
		bodyParam.put("accountid", privateMap.get("accountid"));
		
		bodyParam.put("custname", privateMap.get("custname"));
		//�ʽ���㷽ʽ Ĭ��3
		bodyParam.put("finatype", privateMap.get("finatype"));
		//Ĭ����902
		bodyParam.put("paychannelcode", privateMap.get("paychannelcode"));
		//�Ǳش�
		bodyParam.put("certificatetype", privateMap.get("certificatetype"));
		//�Ǳش�
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
			logger.info("Ⱥ�ýӿ�:" + trancode + " ��Σ�" + recvMessage);
			String methodName="service";
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("arg0", recvMessage);
			String result = WebServiceUtil.callMethod(methodName,param);
			logger.info("Ⱥ�ýӿ�:"+trancode +" ���ز�����"+ result +" \n����ʱ��" + (System.currentTimeMillis() - start_time) +"ms");
			return (JSONObject) BaseJSONUtils.mapToJson(BaseXMLUtil.xmltoMap(result));
		} catch (Exception e) {
			QJException dle = new QJException(e);
			//String errorMsg = dle.errorMsg();
			String errorClass = dle.className();
			logger.error("ϵͳ�쳣",e);
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

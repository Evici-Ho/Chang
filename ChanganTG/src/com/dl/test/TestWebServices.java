package com.dl.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.dl.middleware.utils.WebServiceUtil;



public class TestWebServices {

	public static void main(String[] args)  {

		/*String targetNamespace="http://www.webserviceX.NET";
		String oper = "GetWeather";
		String endpoint = "http://www.webservicex.net/globalweather.asmx?wsdl";
		//String endpoint = "http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?wsdl";
		Service serivce = new Service();
		Call call = (Call) serivce.createCall();
		//call.setTargetEndpointAddress(endpoint);
		call.setTargetEndpointAddress(new URL(endpoint));
		call.setOperationName(new QName(targetNamespace,oper));
		
        call.addParameter(new QName(targetNamespace, "CityName"), org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        call.addParameter(new QName(targetNamespace, "CountryName"), org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		
		// 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(targetNamespace + "/" + oper);
		String result = (String) call.invoke(new Object[] {"shenzhen", "China"});
		System.out.println(result);*/
		
		System.out.println(remoteCall());
			
	}
	
	public static String remoteCall() {

		String targetNamespace = "http://www.webserviceX.NET";
		String serviceName = "GetWeather";
		String endpoint = "http://www.webservicex.net/globalweather.asmx?wsdl";
		String result = "";
		try {
			Service serivce = new Service();
			Call call = (Call) serivce.createCall();
			// call.setTargetEndpointAddress(endpoint);
			call.setTargetEndpointAddress(new URL(endpoint));
			call.setOperationName(new QName(targetNamespace, serviceName));

			call.addParameter(new QName(targetNamespace, "CityName"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName(targetNamespace, "CountryName"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			// 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(targetNamespace + "/" + serviceName);
			result = (String) call.invoke(new Object[] { "shenzhen", "China" });
		} catch (ServiceException e) {
			System.out.println(e);
		} catch (MalformedURLException e) {
			System.out.println(e);
		} catch (RemoteException e) {
			System.out.println(e);
		}
		return result;
	}
	
	/*public static void main(String[] args) throws Exception {
		 //服务的地址
       URL wsUrl = new URL("http://www.webservicex.net/globalweather.asmx?wsdl");
       
       HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
       
       conn.setDoInput(true);
       conn.setDoOutput(true);
       conn.setRequestMethod("POST");
       conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
       
       OutputStream os = conn.getOutputStream();
       
       //请求体
       String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://ws.itcast.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
                     "<soapenv:Body> <q0:GetWeather><arg0>aaa</arg0>  </q0:GetWeather> </soapenv:Body> </soapenv:Envelope>";
       
       os.write(soap.getBytes());
       
       InputStream is = conn.getInputStream();
       
       byte[] b = new byte[1024];
       int len = 0;
       String s = "";
       while((len = is.read(b)) != -1){
           String ss = new String(b,0,len,"UTF-8");
           s += ss;
       }
       System.out.println(s);
       
       is.close();
       os.close();
       conn.disconnect();
	}*/

}

package com.dl.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.json.JSONObject;

import com.dl.middleware.utils.UrlUtil;

public class Test3 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx5bf99c76127b5974&secret=a326280c4caebf41b1d59e45c35c0f95&code=https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code&grant_type=authorization_code";
		//String jsonResult = UrlUtil.openUrl("https://api.weixin.qq.com/cgi-bin/user/info", param);
		
		System.out.println(Class.class.getClass().getResource("/").getPath() + "wechat/wechatMenus.json");
		/*URL url =  new URL(urlStr);
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
		System.out.println(result);*/
		
		createMenu();
	}
	
	private static String getAccess_token() {

		//String APPID = "wx42153f5efcec6e55";
		//String APPSECRET = "f9d52283992ef014f9d216854626885f";
		String APPID= "wx5bf99c76127b5974";
		String APPSECRET = "a326280c4caebf41b1d59e45c35c0f95";

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret="
				+ APPSECRET;
		String accessToken = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");

			JSONObject demoJson = new JSONObject(message);
			accessToken = demoJson.getString("access_token");

			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
	public static String ReadFile(String path) throws FileNotFoundException {
		File file = new File(path);
		BufferedReader reader = null;
		InputStreamReader isr = null;
		InputStream in = new FileInputStream(file);

		String laststr = "";
		try {
			// isr = new InputStreamReader(new FileInputStream(file),
			// Charset.defaultCharset().name());
			// 可检测多种类型，并剔除bom
			BOMInputStream bomIn = new BOMInputStream(in, false,
					ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE,
					ByteOrderMark.UTF_16BE);
			String charset = "utf-8";
			// 若检测到bom，则使用bom对应的编码
			if (bomIn.hasBOM()) {
				charset = bomIn.getBOMCharsetName();
			}
			isr = new InputStreamReader(bomIn, charset);

			reader = new BufferedReader(isr);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				tempString = tempString.replaceAll(" |\t", "");
				laststr = laststr + tempString;
			}
			bomIn.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}

	public static String createMenu() throws FileNotFoundException {
		
		/*String port = ":" + request.getServerPort();
		if(port.equals(":80")){
		    port = "";
		}
		String basePath = request.getScheme() + "://"+ request.getServerName() + port+ path + "/";*/
		//String uu = "\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bf99c76127b5974&redirect_uri=http://1.changantg.sinaapp.com/Flow.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"";
		//String menu = "{\"button\":[{\"type\":\"view\",\"name\":\"MENU02\",\"url\":" + uu + "},{\"type\":\"view\",\"name\":\"天气查询\",\"url\":" + uu +"},{\"name\":\"日常工作\",\"sub_button\":[{\"type\":\"click\",\"name\":\"待办工单\",\"key\":\"01_WAITING\"},{\"type\":\"click\",\"name\":\"已办工单\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"我的工单\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"公告消息箱\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"签到\",\"key\":\"05_SIGN\"}]}]}";
		String menu = ReadFile(Class.class.getClass().getResource("/").getPath() + "wechat/wechatMenus.json");
		System.out.println(menu);
		// 此处改为自己想要的结构体，替换即可
		String access_token = getAccess_token();
		String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(menu.getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			return "返回信息" + message;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "createMenu 失败";
	}

}

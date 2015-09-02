package com.dl.wechat.java.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.dl.utils.base.LoadProperties;
import com.dl.wechat.java.message.handler.EventMessageService;
import com.dl.wechat.java.message.handler.TextMessageService;
import com.dl.wechat.java.utils.WechatUtils;

@Component("DLWeiXinFilter")
public class DLWeiXinFilter implements Filter {

	private static final long serialVersionUID = -5021188348833856475L;
	private final Logger logger	= Logger.getLogger(DLWeiXinFilter.class);
	private String _token;
	private static Properties properties;
	//private MessageHandlerImpl messageHandler;

	public void init(FilterConfig config) throws ServletException {
		String propertiesPath = "classPath:wechat/wechat.properties";
		properties =  LoadProperties.load(propertiesPath);
		logger.info("DLWeiXinFilter�Ѿ�������");
	}
	
	public void destroy() {
		logger.info("DLWeiXinFilter�Ѿ����٣�");
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Boolean isGet = request.getMethod().equals("GET");
		String path = request.getServletPath();
		String pathInfo = path.substring(path.lastIndexOf("/"));

		if (pathInfo == null) {
			response.getWriter().write("error");
		} else {
			_token = pathInfo.substring(1);
			if (isGet) {
				doGet(request, response);
			} else {
				//logger.info("szlweb_token:"+_token);
				doPost(request, response,_token);
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ΢�ż���ǩ��
		String signature = request.getParameter("signature");
		// ʱ���
		String timestamp = request.getParameter("timestamp");
		// �����
		String nonce = request.getParameter("nonce");
		// ����ַ���
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		// ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
		if (WechatUtils.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response, String _token)
			throws ServletException, IOException {
		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
		// request.setCharacterEncoding("UTF-8");
		// response.setCharacterEncoding("UTF-8");

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		String respContent = properties.getProperty("subscribeMsg");
		//pw.println(WeiXinService.processRequest(request, respContent));
		pw.println(TextMessageService.processRequest(request, respContent));
	}

	private static String getAccess_token() {

		String APPID = "wx42153f5efcec6e55";
		String APPSECRET = "f9d52283992ef014f9d216854626885f";

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret="
				+ APPSECRET;
		String accessToken = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

			http.setRequestMethod("GET"); // ������get��ʽ����
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// ���ӳ�ʱ30��
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // ��ȡ��ʱ30��
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

	public static String createMenu() {
		String menu = "{\"button\":[{\"type\":\"click\",\"name\":\"MENU01\",\"key\":\"1\"},{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"����\"},{\"name\":\"�ճ�����\",\"sub_button\":[{\"type\":\"click\",\"name\":\"���칤��\",\"key\":\"01_WAITING\"},{\"type\":\"click\",\"name\":\"�Ѱ칤��\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"�ҵĹ���\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"������Ϣ��\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"ǩ��\",\"key\":\"05_SIGN\"}]}]}";

		// �˴���Ϊ�Լ���Ҫ�Ľṹ�壬�滻����
		String access_token = getAccess_token();
		String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// ���ӳ�ʱ30��
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // ��ȡ��ʱ30��
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(menu.getBytes("UTF-8"));// �������
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			return "������Ϣ" + message;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "createMenu ʧ��";
	}

}

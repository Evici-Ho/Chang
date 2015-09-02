package com.dl.wechat.java.filter;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dl.wechat.java.message.accept.TextMessageAccept;
import com.dl.wechat.java.message.reply.TextMessageReply;

public class WeiXinService {

	public static String common(String msgType, Map<String, String> requestMap) {
		String respContent = "�������쳣�����Ժ��ԣ�";
		// �ı���Ϣ
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			respContent = "�����͵����ı���Ϣ��";
		}
		// ͼƬ��Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			respContent = "�����͵���ͼƬ��Ϣ��";
		}
		// ����λ����Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			respContent = "�����͵��ǵ���λ����Ϣ��";
		}
		// ������Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			respContent = "�����͵���������Ϣ��";
		}
		// ��Ƶ��Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			respContent = "�����͵�����Ƶ��Ϣ��";
		}
		// �¼�����
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// �¼�����
			String eventType = requestMap.get("Event");
			// ����
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				respContent = "��л����ע��";
			}
			// ȡ������
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
			}
			// �Զ���˵�����¼�
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				// TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
			}
		}
		return respContent;
	}

	public static String processRequest(HttpServletRequest request, String respContent) {
		String respMessage = null;
		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����

			// xml�������
			//Map<String, String> requestMap = MessageUtil.parseXml(request,"");
			Map<String, String> requestMap = null;
			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			
			
			/*TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			//textMessage.setFuncFlag(0);
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);*/
			
			TextMessageReply textMessageReply = null;
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessageReply = new TextMessageReply();
					textMessageReply.setToUserName(fromUserName);
					textMessageReply.setFromUserName(toUserName);
					textMessageReply.setCreateTime(new Date().getTime());
					textMessageReply.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessageReply.setFuncFlag(0);
					textMessageReply.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessageReply);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage; //�������ʾ������""
	}
}

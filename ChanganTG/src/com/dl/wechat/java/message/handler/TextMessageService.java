package com.dl.wechat.java.message.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dl.wechat.java.message.accept.TextMessageAccept;
import com.dl.wechat.java.message.reply.TextMessageReply;
import com.dl.wechat.java.utils.MessageType;

public class TextMessageService extends BaseMessageService {

	public static String processRequest(HttpServletRequest request, String respContent) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			// xml请求解析
			//Map<String, String> requestMap = MessageUtil.parseXml(request);
			TextMessageAccept acceptMsg = (TextMessageAccept)parseXml(request, "com.dl.wechat.java.message.accept.TextMessageAccept");

			// 发送方帐号（open_id）
			//String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			//String toUserName = requestMap.get("ToUserName");
			// 消息类型
			//String msgType = requestMap.get("MsgType");

			
			
			TextMessageReply textMessage = new TextMessageReply();
			textMessage.setToUserName(acceptMsg.getFromUserName());
			textMessage.setFromUserName(acceptMsg.getToUserName());
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageType.RESP_MESSAGE_TYPE_TEXT);
			//textMessage.setFuncFlag(0);
			textMessage.setContent(respContent);
			respMessage = textMessageToXml(textMessage);
			
			/*TextMessageReply textMessageReply = null;
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				// 订阅
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
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage; //如果不提示，返回""
	}
}

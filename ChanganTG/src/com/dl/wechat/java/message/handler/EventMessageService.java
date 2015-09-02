package com.dl.wechat.java.message.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dl.wechat.java.message.accept.EventMessageAccept;
import com.dl.wechat.java.message.reply.TextMessageReply;
import com.dl.wechat.java.utils.MessageType;

public class EventMessageService extends BaseMessageService {

	public static String processRequest(HttpServletRequest request, String respContent) {
		String respMessage = null;
		try {
			// xml请求解析
			EventMessageAccept acceptMsg = (EventMessageAccept)parseXml(request,
					"com.dl.wechat.java.message.accept.EventMessageAccept");

			TextMessageReply textMessageReply = null;
			if (acceptMsg.MsgType.equals(MessageType.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = acceptMsg.getEvent();
				// 订阅
				if (eventType.equals(MessageType.EVENT_TYPE_SUBSCRIBE)) {
					textMessageReply = new TextMessageReply();
					textMessageReply.setToUserName(acceptMsg.getFromUserName());
					textMessageReply.setFromUserName(acceptMsg.getToUserName());
					textMessageReply.setCreateTime(new Date().getTime());
					textMessageReply.setMsgType(MessageType.RESP_MESSAGE_TYPE_TEXT);
					textMessageReply.setFuncFlag(0);
					textMessageReply.setContent(respContent);
					respMessage = textMessageToXml(textMessageReply);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage; // 如果不提示，返回""
	}
}

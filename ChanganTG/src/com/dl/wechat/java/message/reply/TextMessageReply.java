package com.dl.wechat.java.message.reply;

public class TextMessageReply extends BaseMessageReply {
    // 回复的消息内容
    public String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
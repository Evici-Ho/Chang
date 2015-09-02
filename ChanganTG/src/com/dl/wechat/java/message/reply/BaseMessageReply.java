package com.dl.wechat.java.message.reply;

public class BaseMessageReply {
    // 接收方帐号（收到的OpenID）
    public String ToUserName;
    // 开发者微信号
    public String FromUserName;
    // 消息创建时间 （整型）
    public Long CreateTime;
    // 消息类型（text/music/news）
    public String MsgType;
    // 位0x0001被标志时，星标刚收到的消息
    public Integer FuncFlag;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Integer getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(Integer funcFlag) {
        FuncFlag = funcFlag;
    }
}
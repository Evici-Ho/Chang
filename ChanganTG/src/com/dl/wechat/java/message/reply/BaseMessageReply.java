package com.dl.wechat.java.message.reply;

public class BaseMessageReply {
    // ���շ��ʺţ��յ���OpenID��
    public String ToUserName;
    // ������΢�ź�
    public String FromUserName;
    // ��Ϣ����ʱ�� �����ͣ�
    public Long CreateTime;
    // ��Ϣ���ͣ�text/music/news��
    public String MsgType;
    // λ0x0001����־ʱ���Ǳ���յ�����Ϣ
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
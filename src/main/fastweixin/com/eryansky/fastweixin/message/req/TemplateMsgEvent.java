package com.eryansky.fastweixin.message.req;

/**
 * Created by Ben on 2015/6/24.
 */
public class TemplateMsgEvent extends BaseEvent {
    private String msgId;
    private String status;

    public TemplateMsgEvent() {
    }

    public TemplateMsgEvent(String msgId, String status) {
        this.msgId = msgId;
        this.status = status;
    }

    public String getMsgId() {
        return msgId;
    }

    public TemplateMsgEvent setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TemplateMsgEvent setStatus(String status) {
        this.status = status;
        return this;
    }
}

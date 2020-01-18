package com.ultimatech.dockerweb.websocket;

import java.util.Date;

/**
 * Created by zhangleping on 2017/9/18.
 */
public class MsgData {


    private Date createDate=new Date();

    private String fromId;
    private String toId;
    private String content;
    private String ip;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public MsgData(String toId) {
        this.toId = toId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

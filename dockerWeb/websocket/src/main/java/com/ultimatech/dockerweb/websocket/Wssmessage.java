package com.ultimatech.dockerweb.websocket;

import java.util.Date;

/**
 * Created by zhangleping on 2017/1/9.
 */
public class Wssmessage {
    private MsgType type;
    private MsgData data;

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public MsgData getData() {
        return data;
    }

    public void setData(MsgData data) {
        this.data = data;
    }

    public Wssmessage(MsgType type, MsgData data) {
        this.type = type;
        this.data = data;
    }

}

package com.ultimatech.dockerweb.websocket.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ultimatech.dockerweb.websocket.MsgData;
import com.ultimatech.dockerweb.websocket.MySocketSession;
import com.ultimatech.dockerweb.websocket.Wssmessage;
import com.ultimatech.dockerweb.websocket.service.IWebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangleping on 2017/2/9.
 */
@Service("websocketService")
public class WebsocketServiceImpl implements IWebsocketService {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private ConcurrentHashMap<String, MySocketSession> socketSessionMap =
            new ConcurrentHashMap<String, MySocketSession>();

    public ConcurrentHashMap<String, MySocketSession> getSocketSessionMap() {
        return socketSessionMap;
    }

    public void setSocketSessionMap(ConcurrentHashMap<String, MySocketSession> socketSessionMap) {
        this.socketSessionMap = socketSessionMap;
    }

    private Logger log = LoggerFactory.getLogger(WebsocketServiceImpl.class);

    @Override
    public void sendMsg(Wssmessage msg) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(msg);
        if (msg.getData() != null) {
            MsgData data = msg.getData();
            switch (msg.getType()) {
                case connectioned:
                    sendOne(msg, json);
                    break;
                case online:
                    sendAll(json);
                    break;
                case offline:
                    sendAll(json);
                    break;
                case say:
                    sendOne(msg, json);
                default:
                    break;
            }
        }

    }

    private void sendAll(String json) {
        Iterator it = this.getSocketSessionMap().keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            MySocketSession session = this.getSocketSessionMap().get(key);
            session.sendMsg(json);
        }
    }

    private void sendOne(Wssmessage msg, String json) {
        if (this.getSocketSessionMap().containsKey(msg.getData().getToId())) {
            MySocketSession session = this.getSocketSessionMap().get(msg.getData().getToId());
            if (session != null)
                session.sendMsg(json);
            else {
                log.debug("客户端不存在map中，id:" + msg.getData().getToId());
            }
        } else {
            log.debug("map中不存在key值，id:" + msg.getData().getToId());
        }
    }

    @Override
    public void addSocketSession(String openid, MySocketSession session) {
        this.getSocketSessionMap().put(openid, session);
    }

    @Override
    public void removeSocketSession(String openid) {
        this.getSocketSessionMap().remove(openid);
    }

    @Override
    public int getSocketSessionCnt() {
        return this.getSocketSessionMap().size();
    }
}

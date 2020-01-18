package com.ultimatech.dockerweb.websocket.service;


import com.ultimatech.dockerweb.websocket.MySocketSession;
import com.ultimatech.dockerweb.websocket.Wssmessage;

import java.io.IOException;

/**
 * Created by zhangleping on 2017/2/9.
 */
public interface IWebsocketService {

    void sendMsg(Wssmessage msg) throws IOException;

    void addSocketSession(String openid, MySocketSession session);

    void removeSocketSession(String openid);

    int getSocketSessionCnt();

}

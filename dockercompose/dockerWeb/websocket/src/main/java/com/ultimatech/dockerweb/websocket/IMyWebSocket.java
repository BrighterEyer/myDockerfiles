package com.ultimatech.dockerweb.websocket;

import javax.websocket.Session;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by zhangleping on 2017/2/10.
 */
public interface IMyWebSocket {

    /**
     * 连接建立成功调用的方法
     */
    public void onOpen(Session session) throws IOException ;

    /**
     * 连接关闭调用的方法
     */
    public void onClose(Session session) throws IOException;


    /**
     * 收到客户端消息后调用的方法
     */
    public void onMessage(String json, Session session) ;

    /**
     * 发生错误时调用
     **/
    public void onError(Session session, Throwable error);

}

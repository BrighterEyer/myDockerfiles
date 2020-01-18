package com.ultimatech.dockerweb.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;

/**
 * Created by zhangleping on 2017/3/30.
 */
public class MySocketSession {

    private Logger log= LoggerFactory.getLogger(MySocketSession.class);

    private Session session;

    private String openid;

    public MySocketSession(Session session, String openid) {
        this.session = session;
        this.openid = openid;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void sendMsg(String message){
        //        this.session.getBasicRemote().sendText(message);
        RemoteEndpoint.Async remoteEndpoint = session.getAsyncRemote();
//        RemoteEndpoint.Basic remoteEndpoint = this.session.getBasicRemote();
        log.debug("RemoteEndpoint type:" + remoteEndpoint.getClass().getSimpleName());
//        remoteEndpoint.sendText(message, false);
        remoteEndpoint.sendText(message, new SendHandler() {
            @Override
            public void onResult(SendResult sendResult) {
                log.debug("isOk:" + sendResult.isOK());
                log.debug("message is:" + message);
                Throwable ex = sendResult.getException();
                if (ex != null) {
                    log.error("发送消息出错", ex);
                }
            }
        });
    }
}

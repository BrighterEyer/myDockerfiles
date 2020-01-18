package com.ultimatech.dockerweb.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ultimatech.dockerweb.websocket.service.IWebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangleping on 2017/1/9.
 */
@ServerEndpoint(value = "/wss/", configurator = MySpringConfigurator.class)
@Component("myWebsocket")
public class MyWebSocket implements IMyWebSocket, Serializable {

    private ConcurrentHashMap<String, String> SESSIONID_OPENID_MAP = new ConcurrentHashMap();

    private Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    @Resource(name = "kafkaWebsocketService")
    private IWebsocketService websocketService;

    public IWebsocketService getWebsocketService() {
        return websocketService;
    }

    public void setWebsocketService(IWebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        addOnlineCount();           //在线数加1
        String openid = session.getRequestParameterMap().get("openid") == null
                || session.getRequestParameterMap().get("openid").size() == 0
                ? null : session.getRequestParameterMap().get("openid").get(0);
        MySocketSession mySocketSession = new MySocketSession(session, openid);
        websocketService.addSocketSession(openid, mySocketSession);
        this.SESSIONID_OPENID_MAP.put(session.getId(), openid);
        logger.debug("有新连接加入！当前在线人数为" + websocketService.getSocketSessionCnt());
        logger.debug("加入者客户端id：" + openid);
        MsgData data = new MsgData(openid);
        data.setFromId(openid);
        data.setIp(InetAddress.getLocalHost().getHostAddress());
        data.setContent("链接成功.");
        Wssmessage wssmessage = new Wssmessage(MsgType.connectioned, data);
        websocketService.sendMsg(wssmessage);
        MsgData online = new MsgData("");
        online.setFromId(openid);
        online.setIp(InetAddress.getLocalHost().getHostAddress());
        online.setContent(openid + "在服务器“" + data.getIp() + "”上线了");
        Wssmessage onlinewss = new Wssmessage(MsgType.online, online);
        websocketService.sendMsg(onlinewss);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        String openid = this.SESSIONID_OPENID_MAP.get(session.getId());
        websocketService.removeSocketSession(this.SESSIONID_OPENID_MAP.get(session.getId()));  //从set中删除
        logger.debug("移除openid对应的session：" + this.SESSIONID_OPENID_MAP.get(session.getId()));
        this.SESSIONID_OPENID_MAP.remove(session.getId());
        logger.debug("移除session id 对应的openid：" + session.getId());
        subOnlineCount();           //在线数减1
        logger.debug("有一连接关闭！当前在线人数为" + websocketService.getSocketSessionCnt());
        MsgData offline = new MsgData("");
        offline.setFromId(openid);
        String ip = InetAddress.getLocalHost().getHostAddress();
        offline.setIp(ip);
        offline.setContent(openid + "在服务器“" + ip + "”下线了");
        Wssmessage offlinewss = new Wssmessage(MsgType.offline, offline);
        websocketService.sendMsg(offlinewss);
    }


    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String json, Session session) {
        logger.info("来自客户端的消息:" + json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        Gson gson = gsonBuilder.create();

    }

    /**
     * 发生错误时调用
     **/
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("websocket发生错误", error);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}

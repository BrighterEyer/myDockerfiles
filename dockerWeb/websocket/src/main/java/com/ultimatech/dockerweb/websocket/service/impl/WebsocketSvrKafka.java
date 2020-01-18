package com.ultimatech.dockerweb.websocket.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ultimatech.dockerweb.kafka.KafkaMessage;
import com.ultimatech.dockerweb.kafka.service.IKafkaService;
import com.ultimatech.dockerweb.websocket.Wssmessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

/**
 * Created by zhangleping on 2017/9/20.
 */
@Service("kafkaWebsocketService")
public class WebsocketSvrKafka extends WebsocketServiceImpl {

    private Logger log= LoggerFactory.getLogger(WebsocketSvrKafka.class);

    private String chatTopic="chattopic";

    @Autowired
    private IKafkaService kafkaService;

    private Gson gson=new GsonBuilder().create();

    @Override
    public void sendMsg(Wssmessage msg) throws IOException {
        msg.getData().setIp(InetAddress.getLocalHost().getHostAddress());
        KafkaMessage kafkaMessage=new KafkaMessage();
        kafkaMessage.setMsg(gson.toJson(msg));
        kafkaMessage.setSendTime(new Date());
        kafkaService.sendMessage(this.chatTopic,kafkaMessage);
    }

    @KafkaListener(topics = "chattopic")
    public void processMessage(String content) throws IOException {
        KafkaMessage kafkaMessage=gson.fromJson(content,KafkaMessage.class);
        Wssmessage wssmessage=gson.fromJson(kafkaMessage.getMsg(),Wssmessage.class);
        super.sendMsg(wssmessage);
    }
}

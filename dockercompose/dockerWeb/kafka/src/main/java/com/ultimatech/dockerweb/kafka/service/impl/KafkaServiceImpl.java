package com.ultimatech.dockerweb.kafka.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ultimatech.dockerweb.kafka.KafkaMessage;
import com.ultimatech.dockerweb.kafka.service.IKafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by zhangleping on 2017/9/20.
 */
@Service("kafkaService")
public class KafkaServiceImpl implements IKafkaService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private Gson gson = new GsonBuilder().create();


    @Override
    public void sendMessage(String topic, KafkaMessage message) {
        String json = gson.toJson(message);
        this.kafkaTemplate.send(topic, json);
    }


}

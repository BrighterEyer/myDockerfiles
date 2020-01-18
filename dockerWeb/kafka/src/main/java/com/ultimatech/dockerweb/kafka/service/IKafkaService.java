package com.ultimatech.dockerweb.kafka.service;

import com.ultimatech.dockerweb.kafka.KafkaMessage;

/**
 * Created by zhangleping on 2017/9/20.
 */
public interface IKafkaService {

    void sendMessage(String topic, KafkaMessage message);
}

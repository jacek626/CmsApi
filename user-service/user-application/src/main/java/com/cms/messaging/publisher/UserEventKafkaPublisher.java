package com.cms.messaging.publisher;

import com.cms.api.KafkaProducer;
import com.cms.api.KafkaProducerProperties;
import com.cms.avro.model.UserEventAvro;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventKafkaPublisher {

    private final KafkaProducer<String, UserEventAvro> kafkaProducer;
    private final KafkaProducerProperties kafkaProducerProperties;


    public void publish(String id, UserEventAvro userEventAvro) {
        kafkaProducer.send(kafkaProducerProperties.getTopicName(), id, userEventAvro, null);
    }



}

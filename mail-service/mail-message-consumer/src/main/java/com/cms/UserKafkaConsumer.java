package com.cms;

import com.cms.api.EmailService;
import com.cms.api.KafkaConsumer;
import com.cms.avro.model.UserEventAvro;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserKafkaConsumer implements KafkaConsumer<UserEventAvro> {

    @Override
    @KafkaListener(id = "${kafka-consumer-config.user-group-id}", topics = "${user-service.user-created-topic}")
    public void receive(List<UserEventAvro> messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        new EmailService().sendEmail();
        System.out.println("XXXXXXXXXX");
        System.out.println("user-service.user-created-topic-name");
    }
}

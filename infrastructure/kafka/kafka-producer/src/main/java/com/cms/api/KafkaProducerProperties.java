package com.cms.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@Getter
@Setter
public class KafkaProducerProperties {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    String bootstrapServers;

    @Value("${user-service.user-created-topic}")
    String topicName;

    @Value("${spring.kafka.producer.schema.registry.url}")
    String schemaRegistryUrl;
}

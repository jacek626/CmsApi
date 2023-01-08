package com.cms;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableJpaRepositories
//@EntityScan(basePackages = { "com.cms"})
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
      SpringApplication.run(UserServiceApplication.class, args);
    }
}


@Configuration
@AllArgsConstructor
@EnableKafka
 class KafkaConfig {

    private final KafkaProperties kafkaProperties;

  /*  @Bean
    public ProducerFactory<String, UserEvent> producerFactory() {
        // get configs on application.properties/yml
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }
*/
/*    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }*/

/*    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }*/


    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name("userTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
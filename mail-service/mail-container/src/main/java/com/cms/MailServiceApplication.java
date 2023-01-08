package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EntityScan(basePackages = { "com.cms"})
@ConfigurationPropertiesScan("com.cms")
public class MailServiceApplication {
    public static void main(String[] args) {
      SpringApplication.run(MailServiceApplication.class, args);
    }
}



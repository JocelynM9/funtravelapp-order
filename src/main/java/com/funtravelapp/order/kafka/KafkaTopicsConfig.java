package com.funtravelapp.order.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {
    @Bean
    public NewTopic createPayment(){
        return TopicBuilder.name("CreatePayment").build();
    }
    @Bean
    public NewTopic updateStatusOrder(){
        return TopicBuilder.name("UpdateStatusOrder").build();
    }
}

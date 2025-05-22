package com.csniico.task_api.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic taskCreatedTopic() {
        return new NewTopic("task.created", 3, (short) 1);
    }

    @Bean
    public NewTopic taskUpdatedTopic() {
        return new NewTopic("task.updated", 3, (short) 1);
    }
}

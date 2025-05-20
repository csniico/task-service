package com.csniico.task_api.service;

import com.csniico.task_api.dto.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TaskMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("task.created", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendTaskToTopic(TaskRequest task) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("task.created", task);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + task.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" + task.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Unable to send message=[" + task.toString() + "] due to : " + e.getMessage());
        }
    }

    public void sendTaskUpdateToTopic(TaskRequest task) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("task.updated", task);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + task.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" + task.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Unable to send message=[" + task.toString() + "] due to : " + e.getMessage());
        }
    }
}

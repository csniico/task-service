package com.csniico.task_api.controller;

import com.csniico.task_api.dto.TaskRequest;
import com.csniico.task_api.service.TaskCRUDService;
import com.csniico.task_api.service.TaskMessagePublisher;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/api/v1")
public class EventController {

    private final TaskMessagePublisher publisher;
    TaskCRUDService taskCRUDService;

    public EventController(TaskMessagePublisher publisher, TaskCRUDService taskCRUDService) {
        this.publisher = publisher;
        this.taskCRUDService = taskCRUDService;
    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        try {
            for (int i=0; i<1_000_000_000; i++)
                publisher.sendMessageToTopic(message + " : " + i);
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/create/")
   public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        try {
            boolean created = taskCRUDService.createTask(taskRequest);
            if (created) {
                publisher.sendTaskToTopic(taskRequest);
                return ResponseEntity.ok("Task created successfully");
            }
            else {
                return ResponseEntity.internalServerError().body("Task creation failed");
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
   }
}
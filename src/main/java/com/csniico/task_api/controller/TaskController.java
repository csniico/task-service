package com.csniico.task_api.controller;

import com.csniico.task_api.dto.ApiResponse;
import com.csniico.task_api.dto.TaskRequest;
import com.csniico.task_api.dto.TaskResponse;
import com.csniico.task_api.service.TaskCRUDService;
import com.csniico.task_api.service.TaskMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCRUDService taskCRUDService;
    private final TaskMessagePublisher taskMessagePublisher;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {
        List<TaskResponse> tasks = taskCRUDService.getAllTasks();
        ApiResponse<List<TaskResponse>> response = new ApiResponse<>("Tasks retrieved successfully", tasks);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable int id) {
        TaskResponse task = taskCRUDService.getTaskById(id);
        if (task != null) {
            ApiResponse<TaskResponse> response = new ApiResponse<>("Task retrieved successfully", task);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByUser(@RequestParam String email) {
        List<TaskResponse> tasks = taskCRUDService.getTasksByUser(email);
        ApiResponse<List<TaskResponse>> response = new ApiResponse<>("Tasks retrieved successfully", tasks);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<String>> markTaskAsCompleted(
        @RequestParam int id,
        @RequestBody Map<String, String> requestBody
    ) {
        String status = requestBody.get("status");

        if (status != null) {
            boolean success = taskCRUDService.updateTaskStatus(id, status);
            if (success) {
                TaskResponse updatedTask = taskCRUDService.getTaskById(id);

                if (updatedTask != null) {
                    TaskRequest taskRequest = getTaskRequest(updatedTask);
                    taskMessagePublisher.sendTaskUpdateToTopic(taskRequest);
                }

                ApiResponse<String> response = new ApiResponse<>("Task status updated successfully", null);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request body", null));
        }
    }

    private static TaskRequest getTaskRequest(TaskResponse updatedTask) {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setId(updatedTask.getId());
        taskRequest.setTitle(updatedTask.getTitle());
        taskRequest.setDescription(updatedTask.getDescription());
        taskRequest.setStatus(updatedTask.getStatus());
        taskRequest.setPriority(updatedTask.getPriority());
        taskRequest.setAssignedTo(updatedTask.getAssignedTo());
        taskRequest.setDueDate(updatedTask.getDueDate());
        taskRequest.setCategory(updatedTask.getCategory());
        return taskRequest;
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateTask(@RequestBody TaskRequest taskRequest, @RequestParam int id) {
        boolean success = taskCRUDService.updateTask(taskRequest, id);
        if (success) {
            // Retrieve the updated task for publishing
            TaskResponse updatedTask = taskCRUDService.getTaskById(id);

            if (updatedTask != null) {
                TaskRequest updatedTaskRequest = getTaskRequest(updatedTask);
                taskMessagePublisher.sendTaskUpdateToTopic(updatedTaskRequest);
            }

            ApiResponse<String> response = new ApiResponse<>("Task status updated successfully", null);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteTask(@RequestParam int id) {
        boolean success = taskCRUDService.deleteTask(id);
        if (success) {
            ApiResponse<String> response = new ApiResponse<>("Task deleted successfully", null);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
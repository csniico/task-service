package com.csniico.task_api.controller;

import com.csniico.task_api.dto.ApiResponse;
import com.csniico.task_api.dto.TaskRequest;
import com.csniico.task_api.dto.TaskResponse;
import com.csniico.task_api.service.TaskCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCRUDService taskCRUDService;

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
    public ResponseEntity<ApiResponse<String>> markTaskAsCompleted(@RequestParam int id) {
        boolean success = taskCRUDService.markAsCompleted(id);
        if (success) {
            ApiResponse<String> response = new ApiResponse<>("Task marked as completed successfully", null);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateTask(@RequestBody TaskRequest taskRequest, @RequestParam int id) {
        boolean success = taskCRUDService.updateTask(taskRequest, id);
        if (success) {
            ApiResponse<String> response = new ApiResponse<>("Task updated successfully", null);
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

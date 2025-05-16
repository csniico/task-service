package com.csniico.task_api.controller;

import com.csniico.task_api.dto.ApiResponse;
import com.csniico.task_api.dto.TaskResponse;
import com.csniico.task_api.service.TaskCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

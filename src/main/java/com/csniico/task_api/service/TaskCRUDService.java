package com.csniico.task_api.service;

import com.csniico.task_api.Entity.Task;
import com.csniico.task_api.Repository.TaskRepository;
import com.csniico.task_api.dto.TaskRequest;
import com.csniico.task_api.dto.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskCRUDService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskCRUDService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean createTask(TaskRequest taskRequest) {
        try {
            Task task = new Task();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setStatus(taskRequest.getStatus());
            task.setPriority(taskRequest.getPriority());
            task.setAssignedTo(taskRequest.getAssignedTo());
            task.setDueDate(taskRequest.getDueDate());
            task.setCategory(taskRequest.getCategory());
            taskRepository.save(task);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateTask(TaskRequest taskRequest, int id) {
        try {
            Task task = taskRepository.findById((long) id).orElse(null);
            if (task != null) {
                task.setTitle(taskRequest.getTitle());
                task.setDescription(taskRequest.getDescription());
                task.setStatus(taskRequest.getStatus());
                task.setPriority(taskRequest.getPriority());
                task.setAssignedTo(taskRequest.getAssignedTo());
                task.setDueDate(taskRequest.getDueDate());
                task.setCategory(taskRequest.getCategory());
                taskRepository.save(task);
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteTask(int id) {
        try {
            Task task = taskRepository.findById((long) id).orElse(null);
            if (task != null) {
                taskRepository.delete(task);
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateTaskStatus(int id, String status) {
        try {
            Task task = taskRepository.findById((long) id).orElse(null);
            if (task != null) {
                task.setStatus(status);
                taskRepository.save(task);
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public List<TaskResponse> getAllTasks() {
        try {
            List<Task> tasks = taskRepository.findAll();
            return tasks.stream()
                    .map(this::mapToTaskResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Collections.emptyList();
        }
    }

    public List<TaskResponse> getTasksByUser(String email) {
        try {
            List<Task> tasks = taskRepository.findAll();
            return tasks.stream()
                    .filter(task -> Arrays.stream(task.getAssignedTo())
                          .anyMatch(entry -> entry.split(":")[1].equals(email)))
                    .map(this::mapToTaskResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Collections.emptyList();
        }
    }

    public TaskResponse getTaskById(int id) {
        try {
            Task task = taskRepository.findById((long) id).orElse(null);
            if (task != null) {
                return mapToTaskResponse(task);
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private TaskResponse mapToTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId().intValue());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setAssignedTo(task.getAssignedTo());
        response.setDueDate(task.getDueDate());
        response.setCategory(task.getCategory());
        return response;
    }

}
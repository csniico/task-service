package com.csniico.task_api.service;

import com.csniico.task_api.Entity.Task;
import com.csniico.task_api.Repository.TaskRepository;
import com.csniico.task_api.dto.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            taskRepository.save(task);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateTask(TaskRequest taskRequest) {
        try {
            Task task = taskRepository.findById((long) taskRequest.getId()).orElse(null);
            if (task != null) {
                task.setTitle(taskRequest.getTitle());
                task.setDescription(taskRequest.getDescription());
                task.setStatus(taskRequest.getStatus());
                task.setPriority(taskRequest.getPriority());
                task.setAssignedTo(taskRequest.getAssignedTo());
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

    public boolean markAsCompleted(int id) {
        try {
            Task task = taskRepository.findById((long) id).orElse(null);
            if (task != null) {
                task.setStatus("Completed");
                taskRepository.save(task);
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}

package com.csniico.task_api.dto;

import lombok.Data;

@Data
public class TaskRequest {

    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String[] assignedTo;
    private String dueDate;
    private String category;
}

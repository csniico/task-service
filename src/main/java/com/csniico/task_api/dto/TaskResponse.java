package com.csniico.task_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String[] assignedTo;
}

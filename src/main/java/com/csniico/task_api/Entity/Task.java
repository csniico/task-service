package com.csniico.task_api.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "task")
@Data
public class Task {

    @jakarta.persistence.Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;

    @Column(name = "assigned_to")
    private String[] assignedTo;
}

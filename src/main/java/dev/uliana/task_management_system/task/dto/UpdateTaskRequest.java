package dev.uliana.task_management_system.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateTaskRequest {
    private String title;

    private String description;

    private String status;

    private String priority;

    private String authorEmail;

    private String assigneeEmail;
}
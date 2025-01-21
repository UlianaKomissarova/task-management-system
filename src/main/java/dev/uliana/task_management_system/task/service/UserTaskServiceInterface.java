package dev.uliana.task_management_system.task.service;

import dev.uliana.task_management_system.task.dto.TaskResponse;

public interface UserTaskServiceInterface {
    TaskResponse updateTaskStatus(long id, String status);
}
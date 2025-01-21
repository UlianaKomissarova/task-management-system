package dev.uliana.task_management_system.task.service;

import dev.uliana.task_management_system.task.dto.CreateTaskRequest;
import dev.uliana.task_management_system.task.dto.TaskResponse;

public interface AdminTaskServiceInterface {
    TaskResponse addTask(CreateTaskRequest request);

    void deleteTask(long id);
}
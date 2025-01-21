package dev.uliana.task_management_system.task.service;

import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.dto.UpdateTaskRequest;

public interface AdminModificationServiceInterface {
    TaskResponse updateTask(long id, UpdateTaskRequest request);

    TaskResponse addAssignee(long id, String email);
}
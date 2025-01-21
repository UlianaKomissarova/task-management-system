package dev.uliana.task_management_system.task.service;

import dev.uliana.task_management_system.task.dto.TaskResponse;

import java.util.List;

public interface AdminSearchServiceInterface {
    List<TaskResponse> getTasksByAssigneeId(
        long userId,
        String title,
        String status,
        String priority,
        int page,
        int limit);

    List<TaskResponse> getTasksByAuthorId(
        long userId,
        String title,
        String status,
        String priority,
        int page,
        int limit);
}
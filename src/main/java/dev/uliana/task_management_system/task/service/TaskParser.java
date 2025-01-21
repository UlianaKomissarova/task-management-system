package dev.uliana.task_management_system.task.service;

import dev.uliana.task_management_system.task.exception.InvalidTaskPriorityException;
import dev.uliana.task_management_system.task.exception.InvalidTaskStatusException;
import dev.uliana.task_management_system.task.model.TaskPriority;
import dev.uliana.task_management_system.task.model.TaskStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskParser {
    public TaskStatus parseTaskStatus(String status) {
        try {
            return TaskStatus.valueOf(status);
        } catch (IllegalArgumentException exception) {
            throw new InvalidTaskStatusException(String.format("Невалидный статус: %s", status));
        }
    }

    public TaskPriority parseTaskPriority(String priority) {
        try {
            return TaskPriority.valueOf(priority);
        } catch (IllegalArgumentException exception) {
            throw new InvalidTaskPriorityException(String.format("Невалидный приоритет: %s", priority));
        }
    }
}
package dev.uliana.task_management_system.task.exception;

import dev.uliana.task_management_system.shared.exception.BadRequestException;

public class TaskBadRequestException extends BadRequestException {
    public TaskBadRequestException(String message) {
        super(message);
    }
}
package dev.uliana.task_management_system.task.exception;

import dev.uliana.task_management_system.shared.exception.BadRequestException;

public class InvalidTaskPriorityException extends BadRequestException {
    public InvalidTaskPriorityException(String message) {
        super(message);
    }
}
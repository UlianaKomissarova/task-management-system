package dev.uliana.task_management_system.task.exception;

import dev.uliana.task_management_system.shared.exception.BadRequestException;

public class InvalidTaskStatusException extends BadRequestException {
    public InvalidTaskStatusException(String message) {
        super(message);
    }
}
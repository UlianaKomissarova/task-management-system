package dev.uliana.task_management_system.task.exception;

import dev.uliana.task_management_system.shared.exception.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
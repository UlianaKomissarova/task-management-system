package dev.uliana.task_management_system.user.exception;

import dev.uliana.task_management_system.shared.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
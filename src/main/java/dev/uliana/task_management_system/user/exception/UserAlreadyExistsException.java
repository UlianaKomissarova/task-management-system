package dev.uliana.task_management_system.user.exception;

import dev.uliana.task_management_system.shared.exception.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
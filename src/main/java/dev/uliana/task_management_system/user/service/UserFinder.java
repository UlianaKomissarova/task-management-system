package dev.uliana.task_management_system.user.service;

import dev.uliana.task_management_system.user.exception.UserNotFoundException;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFinder {
    private final UserRepository repository;

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException(String.format("Пользователь с эл.почтой %s не найден", email))
        );
    }

    public User getUserById(long id) {
        return repository.findById(id).orElseThrow(
            () -> new UserNotFoundException(String.format("Пользователь с id %d не найден", id))
        );
    }
}
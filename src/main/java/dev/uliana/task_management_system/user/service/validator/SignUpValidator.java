package dev.uliana.task_management_system.user.service.validator;

import dev.uliana.task_management_system.user.dto.SignUpRequest;
import dev.uliana.task_management_system.user.exception.UserAlreadyExistsException;
import dev.uliana.task_management_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpValidator {
    private final UserRepository repository;

    public void validateDtoForCreation(SignUpRequest request) {
        if (repository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует");
        }

        if (repository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }
    }
}
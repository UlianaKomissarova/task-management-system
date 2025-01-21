package dev.uliana.task_management_system.security.service;

import dev.uliana.task_management_system.user.dto.SignInRequest;
import dev.uliana.task_management_system.user.dto.SignUpRequest;
import dev.uliana.task_management_system.user.exception.UserNotFoundException;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.repository.UserRepository;
import dev.uliana.task_management_system.user.service.mapper.UserMapper;
import dev.uliana.task_management_system.user.service.validator.SignUpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final SignUpValidator signUpValidator;

    private final UserMapper userMapper;

    @Transactional
    public User signup(SignUpRequest request) {
        signUpValidator.validateDtoForCreation(request);
        User user = userMapper.toUser(request, passwordEncoder);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Transactional
    public User authenticate(SignInRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
            () -> new UserNotFoundException(String.format("Пользователь с эл.почтой %s не найден", request.email()))
        );

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        return user;
    }
}
package dev.uliana.task_management_system.security;

import dev.uliana.task_management_system.UserData;
import dev.uliana.task_management_system.security.service.AuthenticationService;
import dev.uliana.task_management_system.user.dto.SignInRequest;
import dev.uliana.task_management_system.user.dto.SignUpRequest;
import dev.uliana.task_management_system.user.exception.UserAlreadyExistsException;
import dev.uliana.task_management_system.user.exception.UserNotFoundException;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.repository.UserRepository;
import dev.uliana.task_management_system.user.service.mapper.UserMapper;
import dev.uliana.task_management_system.user.service.validator.SignUpValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SignUpValidator signUpValidator;

    @Mock
    private UserMapper userMapper;

    @Test
    public void signupSuccess() {
        SignUpRequest request = UserData.getSignUpRequest();
        User user = UserData.getAuthor();

        doNothing().when(signUpValidator).validateDtoForCreation(request);
        when(userMapper.toUser(request, passwordEncoder)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User result = authenticationService.signup(request);

        assertNotNull(result);
        assertEquals(request.email(), result.getEmail());
        assertTrue(result.isEnabled());
        verify(signUpValidator).validateDtoForCreation(request);
        verify(userMapper).toUser(request, passwordEncoder);
        verify(userRepository).save(user);
    }

    @Test
    public void signupValidationUserEmailAlreadyExists() {
        SignUpRequest request = UserData.getSignUpRequest();

        doThrow(new UserAlreadyExistsException("Пользователь с таким email уже существует"))
            .when(signUpValidator).validateDtoForCreation(request);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
            () -> authenticationService.signup(request));

        assertEquals("Пользователь с таким email уже существует", exception.getMessage());
        verify(signUpValidator).validateDtoForCreation(request);
        verifyNoInteractions(userRepository, passwordEncoder, userMapper);
    }

    @Test
    public void signupValidationUserNameAlreadyExists() {
        SignUpRequest request = UserData.getSignUpRequest();

        doThrow(new UserAlreadyExistsException("Пользователь с таким логином уже существует"))
            .when(signUpValidator).validateDtoForCreation(request);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
            () -> authenticationService.signup(request));

        assertEquals("Пользователь с таким логином уже существует", exception.getMessage());
        verify(signUpValidator).validateDtoForCreation(request);
        verifyNoInteractions(userRepository, passwordEncoder, userMapper);
    }

    @Test
    public void authenticateSuccess() {
        SignInRequest request = UserData.getSignInRequest();
        User user = UserData.getAuthor();

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));

        User result = authenticationService.authenticate(request);

        assertNotNull(result);
        assertEquals("author@mail.ru", result.getEmail());
        verify(userRepository).findByEmail(request.email());
        verify(authenticationManager).authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
    }

    @Test
    public void authenticateUserNotFound() {
        String nonExistingEmail = "nonexistent@mail.ru";
        SignInRequest request = new SignInRequest(nonExistingEmail, "password");

        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
            () -> authenticationService.authenticate(request)
        );

        assertEquals(String.format("Пользователь с эл.почтой %s не найден", nonExistingEmail), exception.getMessage());
        verify(userRepository).findByEmail(request.email());
    }
}
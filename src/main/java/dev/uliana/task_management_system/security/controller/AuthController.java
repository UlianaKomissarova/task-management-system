package dev.uliana.task_management_system.security.controller;

import dev.uliana.task_management_system.security.dto.LoginResponse;
import dev.uliana.task_management_system.security.service.AuthenticationService;
import dev.uliana.task_management_system.security.service.JwtService;
import dev.uliana.task_management_system.shared.exception.ErrorResponse;
import dev.uliana.task_management_system.user.dto.SignInRequest;
import dev.uliana.task_management_system.user.dto.SignUpRequest;
import dev.uliana.task_management_system.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Контроллер для авторизации и регистрации")
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Пользователь успешно зарегистрирован",
            content = {@Content(schema = @Schema(implementation = User.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные данные",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Пользователь с таким email или username уже существует",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для регистрации", required = true)
        @RequestBody @Valid SignUpRequest request) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authService.signup(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Успешная авторизация",
            content = {@Content(schema = @Schema(implementation = LoginResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные данные для авторизации",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> signIn(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для авторизации", required = true)
        @RequestBody @Valid SignInRequest request) {

        User authenticatedUser = authService.authenticate(request);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
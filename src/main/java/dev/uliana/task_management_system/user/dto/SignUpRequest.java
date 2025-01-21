package dev.uliana.task_management_system.user.dto;

import dev.uliana.task_management_system.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.uliana.task_management_system.shared.Constants.PASSWORD_REGEX;
import static dev.uliana.task_management_system.shared.Constants.USERNAME_REGEX;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.EMAIL_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.EMAIL_PATTERN_VIOLATION;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.PASSWORD_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.PASSWORD_PATTERN_VIOLATION;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.ROLE_IS_NULL;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.USERNAME_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.USERNAME_PATTERN_VIOLATION;

@Schema(description = "Запрос на регистрацию")
public record SignUpRequest(
    @Schema(description = "Логин пользователя", example = "John")
    @NotBlank(message = USERNAME_IS_BLANK)
    @Pattern(regexp = USERNAME_REGEX, message = USERNAME_PATTERN_VIOLATION)
    String username,

    @Schema(description = "Адрес электронной почты", example = "john@gmail.com")
    @NotBlank(message = EMAIL_IS_BLANK)
    @Email(message = EMAIL_PATTERN_VIOLATION)
    String email,

    @Schema(description = "Пароль", example = "Password@123")
    @NotBlank(message = PASSWORD_IS_BLANK)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_PATTERN_VIOLATION)
    String password,

    @NotNull(message = ROLE_IS_NULL)
    Role role
) {
}
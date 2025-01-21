package dev.uliana.task_management_system.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static dev.uliana.task_management_system.shared.Constants.PASSWORD_REGEX;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.EMAIL_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.EMAIL_PATTERN_VIOLATION;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.PASSWORD_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.PASSWORD_PATTERN_VIOLATION;

@Schema(description = "Запрос на аутентификацию")
public record SignInRequest(
    @Schema(description = "Адрес электронной почты", example = "john@gmail.com")
    @NotBlank(message = EMAIL_IS_BLANK)
    @Email(message = EMAIL_PATTERN_VIOLATION)
    String email,

    @Schema(description = "Пароль", example = "Password@123")
    @NotBlank(message = PASSWORD_IS_BLANK)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_PATTERN_VIOLATION)
    String password
) {
}

package dev.uliana.task_management_system.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Ответ на авторизацию")
@Getter
@Setter
public class LoginResponse {
    @Schema(description = "Токен", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaWF0IjoxNzM3MzY3MzgzLCJleHAiOjE3MzczNzA5ODN9.Z9u_aECBdCxXHlV_B4zeNmT90JrrpGF4msURB3m1Yx4")
    private String token;

    @Schema(description = "Время жизни токена в секундах", example = "3600")
    private long expiresIn;

    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
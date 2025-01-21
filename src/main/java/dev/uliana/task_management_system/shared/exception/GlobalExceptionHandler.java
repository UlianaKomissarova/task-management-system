package dev.uliana.task_management_system.shared.exception;

import dev.uliana.task_management_system.user.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Tag(name = "Глобальный обработчик ошибок")
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Operation(summary = "Обработка ошибок BadRequest")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @ExceptionHandler({BadRequestException.class,
        DataIntegrityViolationException.class,
        MethodArgumentNotValidException.class,
        MethodArgumentTypeMismatchException.class}
    )
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @Operation(summary = "Обработка ошибок AccessDenied")
    @ApiResponse(responseCode = "403", description = "Нет доступа")
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return getErrorResponse(HttpStatus.FORBIDDEN, exception);
    }

    @Operation(summary = "Обработка ошибок NotFound")
    @ApiResponse(responseCode = "404", description = "Данные не найдены")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException exception) {
        return getErrorResponse(HttpStatus.NOT_FOUND, exception);
    }

    @Operation(summary = "Обработка ошибок, вызывающих конфликт")
    @ApiResponse(responseCode = "409", description = "Конфликтная ситуация")
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(ConflictException exception) {
        return getErrorResponse(HttpStatus.CONFLICT, exception);
    }

    @Operation(summary = "Обработка внутренних ошибок сервера")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(Exception exception) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(HttpStatus httpStatus, Exception exception) {
        return new ResponseEntity<>(
            new ErrorResponse(
                httpStatus,
                LocalDateTime.now(),
                exception.getMessage()
            ),
            httpStatus
        );
    }
}
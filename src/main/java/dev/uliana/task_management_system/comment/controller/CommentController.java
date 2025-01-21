package dev.uliana.task_management_system.comment.controller;

import dev.uliana.task_management_system.comment.dto.CommentRequest;
import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.comment.service.CommentServiceInterface;
import dev.uliana.task_management_system.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.uliana.task_management_system.shared.exception.ErrorMessage.COMMENT_TASK_ID_IS_NULL;

@Tag(name = "Контроллер для работы с комментариями")
@RestController
@RequestMapping("/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceInterface commentService;

    @Operation(summary = "Созданение нового комментария к задаче")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Комментарий успешно сохранен",
            content = {@Content(schema = @Schema(implementation = CommentResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Ошибка валидации",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Задача не найдена",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
        @Parameter(description = "Идентификатор задачи", required = true)
        @PathVariable @NotNull(message = COMMENT_TASK_ID_IS_NULL) Long taskId,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные о комментарии", required = true)
        @RequestBody @Valid CommentRequest request) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.addComment(taskId, request));
    }
}
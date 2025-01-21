package dev.uliana.task_management_system.task.controller;

import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.shared.exception.ErrorResponse;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.service.impl.UserTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static dev.uliana.task_management_system.shared.exception.ErrorMessage.STATUS_IS_BLANK;

@Tag(name = "Контроллер для работы пользователей с изменением задач")
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class UserTaskController {
    private final UserTaskService userTaskService;

    @Operation(summary = "Обновление статуса задачи ее исполнителем")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус задачи успешно обновлен",
            content = {@Content(schema = @Schema(implementation = CommentResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Невалидный статус",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Задача не найдена",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateStatus(
        @Parameter(description = "Идентификатор задачи", required = true)
        @PathVariable Long id,

        @Parameter(description = "Новый статус задачи", required = true)
        @RequestParam @NotBlank(message = STATUS_IS_BLANK) String status) {

        return ResponseEntity
            .ok(userTaskService.updateTaskStatus(id, status));
    }
}
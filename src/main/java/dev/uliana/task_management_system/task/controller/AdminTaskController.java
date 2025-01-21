package dev.uliana.task_management_system.task.controller;

import dev.uliana.task_management_system.shared.exception.ErrorResponse;
import dev.uliana.task_management_system.task.dto.CreateTaskRequest;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.service.AdminTaskServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Контроллер для работы администраторов с сохранением и удалением задач")
@RestController
@RequestMapping("/admin/tasks")
@RequiredArgsConstructor
public class AdminTaskController {
    private final AdminTaskServiceInterface taskService;

    @Operation(summary = "Добавление новой задачи")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Задача успешно сохранена",
            content = {@Content(schema = @Schema(implementation = TaskResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Ошибка валидации",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для создания задачи", required = true)
        @RequestBody @Valid CreateTaskRequest request) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(taskService.addTask(request));
    }

    @Operation(summary = "Удаление задачи")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Задача успешно удалена"
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
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
        @Parameter(description = "Идентификатор задачи", required = true)
        @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
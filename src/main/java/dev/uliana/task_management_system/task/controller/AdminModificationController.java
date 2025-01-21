package dev.uliana.task_management_system.task.controller;

import dev.uliana.task_management_system.shared.exception.ErrorResponse;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.dto.UpdateTaskRequest;
import dev.uliana.task_management_system.task.service.AdminModificationServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Контроллер для работы администраторов с изменением задач")
@RestController
@RequestMapping("/admin/tasks")
@RequiredArgsConstructor
public class AdminModificationController {
    private final AdminModificationServiceInterface modificationService;

    @Operation(summary = "Изменение задачи админом")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Задача успешно обновлена",
            content = {@Content(schema = @Schema(implementation = TaskResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Невалидные данные",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Задача не найдена",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
        @Parameter(description = "Идентификатор задачи", required = true)
        @PathVariable Long taskId,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные об обновляемой задаче", required = true)
        @RequestBody UpdateTaskRequest request) {

        return ResponseEntity
            .ok(modificationService.updateTask(taskId, request));
    }

    @Operation(summary = "Добавление исполнителя задачи админом")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Исполнитель успешно добавлен",
            content = {@Content(schema = @Schema(implementation = TaskResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Пользователь уже является исполнителем этой задачи",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Задача не найдена",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @PatchMapping("/{taskId}/assignee")
    public ResponseEntity<TaskResponse> addAssignee(
        @Parameter(description = "Идентификатор задачи", required = true)
        @PathVariable Long taskId,

        @Parameter(description = "Email нового исполнителя задачи", required = true)
        @RequestParam String email) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(modificationService.addAssignee(taskId, email));
    }
}
package dev.uliana.task_management_system.task.controller;

import dev.uliana.task_management_system.shared.exception.ErrorResponse;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.service.AdminSearchServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Контроллер для работы администраторов с поиском задач")
@RestController
@RequestMapping("/admin/tasks")
@RequiredArgsConstructor
public class AdminSearchController {
    private final AdminSearchServiceInterface searchService;

    @Operation(summary = "Поиск задач по идентификатору исполнителя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Задачи успешно возвращены",
            content = {@Content(schema = @Schema(implementation = TaskResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Невалидные данные",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Исполнитель не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<TaskResponse>> getTasksByAssigneeId(
        @Parameter(description = "Идентификатор исполнителя задачи", required = true)
        @PathVariable Long assigneeId,

        @Parameter(description = "Фильтр по название задачи")
        @RequestParam(required = false) String title,

        @Parameter(description = "Фильтр по статусу задачи")
        @RequestParam(required = false) String status,

        @Parameter(description = "Фильтр по приоритету задачи")
        @RequestParam(required = false) String priority,

        @Parameter(description = "Номер текущей страницы")
        @RequestParam(required = false, defaultValue = "0") int page,

        @Parameter(description = "Количество задач на странице")
        @RequestParam(required = false, defaultValue = "10") int limit) {
        return ResponseEntity
            .ok(searchService.getTasksByAssigneeId(assigneeId, title, status, priority, page, limit));
    }

    @Operation(summary = "Поиск задач по идентификатору автора")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Задачи успешно возвращены",
            content = {@Content(schema = @Schema(implementation = TaskResponse.class))}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Невалидные данные",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Автор не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<TaskResponse>> getTasksByAuthorId(
        @Parameter(description = "Идентификатор автора задачи", required = true)
        @PathVariable Long authorId,

        @Parameter(description = "Фильтр по название задачи")
        @RequestParam(required = false) String title,

        @Parameter(description = "Фильтр по статусу задачи")
        @RequestParam(required = false) String status,

        @Parameter(description = "Фильтр по приоритету задачи")
        @RequestParam(required = false) String priority,

        @Parameter(description = "Номер текущей страницы")
        @RequestParam(required = false, defaultValue = "0") int page,

        @Parameter(description = "Количество задач на странице")
        @RequestParam(required = false, defaultValue = "10") int limit) {
        return ResponseEntity
            .ok(searchService.getTasksByAuthorId(authorId, title, status, priority, page, limit));
    }
}
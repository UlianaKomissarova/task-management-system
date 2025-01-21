package dev.uliana.task_management_system.task.dto;

import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.task.model.TaskPriority;
import dev.uliana.task_management_system.task.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "Данные задачи для возвращения ответа")
public class TaskResponse {
    @Schema(description = "Уникальный идентификатор задачи", example = "1")
    private Long id;

    @Schema(description = "Название задачи", example = "Перекрасить кнопку")
    private String title;

    @Schema(description = "Описание задачи", example = "В розовый цвет")
    private String description;

    @Schema(description = "Статус задачи", example = "DONE")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", example = "HIGH")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Schema(description = "Уникальный идентификатор автора задачи", example = "1")
    private Long authorId;

    @Schema(description = "Уникальный идентификатор исполнителя задачи", example = "1")
    private Long assigneeId;

    @Schema(description = "Список комментариев задачи")
    private List<CommentResponse> comments;
}
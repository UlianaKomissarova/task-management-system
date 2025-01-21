package dev.uliana.task_management_system.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "Данные комментария для возвращения ответа")
public class CommentResponse {
    @Schema(description = "Уникальный идентификатор комментария", example = "1")
    private Long id;

    @Schema(description = "Текст комментария", example = "Хороший товар, качество на высоте")
    private String text;

    @Schema(description = "Уникальный идентификатор автора", example = "1")
    private Long authorId;

    @Schema(description = "Уникальный идентификатор задачи", example = "1")
    private Long taskId;
}
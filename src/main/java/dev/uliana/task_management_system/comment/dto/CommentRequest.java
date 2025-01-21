package dev.uliana.task_management_system.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static dev.uliana.task_management_system.shared.exception.ErrorMessage.AUTHOR_ID_IS_NULL;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.COMMENT_TEXT_IS_BLANK;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentRequest {
    @NotBlank(message = COMMENT_TEXT_IS_BLANK)
    private String text;

    @NotNull(message = AUTHOR_ID_IS_NULL)
    private Long authorId;
}
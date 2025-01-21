package dev.uliana.task_management_system.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static dev.uliana.task_management_system.shared.exception.ErrorMessage.ASSIGNEE_ID_IS_NULL;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.AUTHOR_ID_IS_NULL;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.DESCRIPTION_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.PRIORITY_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.STATUS_IS_BLANK;
import static dev.uliana.task_management_system.shared.exception.ErrorMessage.TITLE_IS_BLANK;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateTaskRequest {
    @NotBlank(message = TITLE_IS_BLANK)
    private String title;

    @NotBlank(message = DESCRIPTION_IS_BLANK)
    private String description;

    @NotBlank(message = STATUS_IS_BLANK)
    private String status;

    @NotBlank(message = PRIORITY_IS_BLANK)
    private String priority;

    @NotNull(message = AUTHOR_ID_IS_NULL)
    private Long authorId;

    @NotNull(message = ASSIGNEE_ID_IS_NULL)
    private Long assigneeId;
}
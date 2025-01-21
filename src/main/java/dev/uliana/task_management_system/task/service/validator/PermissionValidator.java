package dev.uliana.task_management_system.task.service.validator;

import dev.uliana.task_management_system.task.model.Task;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PermissionValidator {
    public void validateAssigneeAccess(Task task, String currentUserEmail) {
        if (!task.getAssignee().getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("Пользователь может изменить статус только своей задачи");
        }
    }
}
package dev.uliana.task_management_system.task.service.impl;

import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.task.service.UserTaskServiceInterface;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import dev.uliana.task_management_system.task.service.validator.PermissionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTaskService implements UserTaskServiceInterface {
    private final TaskParser taskParser;

    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;

    private final PermissionValidator permissionValidator;

    @Transactional
    public TaskResponse updateTaskStatus(long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(
            () -> new TaskNotFoundException(String.format("Задача с id %d не найдена", id))
        );

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        permissionValidator.validateAssigneeAccess(task, currentUserEmail);

        task.setStatus(taskParser.parseTaskStatus(status));
        Task updatedTask = taskRepository.save(task);

        return taskMapper.toResponse(updatedTask);
    }
}
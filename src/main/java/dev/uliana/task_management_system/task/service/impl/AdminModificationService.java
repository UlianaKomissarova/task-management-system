package dev.uliana.task_management_system.task.service.impl;

import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.dto.UpdateTaskRequest;
import dev.uliana.task_management_system.task.exception.TaskBadRequestException;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.AdminModificationServiceInterface;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminModificationService implements AdminModificationServiceInterface {
    private final TaskRepository taskRepository;

    private final TaskParser taskParser;

    private final TaskMapper taskMapper;

    private final UserFinder userFinder;

    @Transactional
    public TaskResponse updateTask(long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(
            () -> new TaskNotFoundException(String.format("Задача с id %d не найдена", id))
        );

        taskMapper.updateTaskFromRequest(request, task);

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            task.setStatus(taskParser.parseTaskStatus(request.getStatus()));
        }

        if (request.getPriority() != null && !request.getPriority().isBlank()) {
            task.setPriority(taskParser.parseTaskPriority(request.getPriority()));
        }

        if (request.getAuthorEmail() != null && !request.getAuthorEmail().isBlank()) {
            User author = userFinder.getUserByEmail(request.getAuthorEmail());
            task.setAuthor(author);
        }

        if (request.getAssigneeEmail() != null && !request.getAssigneeEmail().isBlank()) {
            User assignee = userFinder.getUserByEmail(request.getAssigneeEmail());
            task.setAssignee(assignee);
        }

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse addAssignee(long id, String email) {
        Task task = taskRepository.findById(id).orElseThrow(
            () -> new TaskNotFoundException(String.format("Задача с id %d не найдена", id))
        );

        if (task.getAssignee().getEmail().equals(email)) {
            throw new TaskBadRequestException(String.format("Пользователь %s уже является исполнителем задачи", email));
        }

        User assignee = userFinder.getUserByEmail(email);
        task.setAssignee(assignee);

        return taskMapper.toResponse(taskRepository.save(task));
    }
}
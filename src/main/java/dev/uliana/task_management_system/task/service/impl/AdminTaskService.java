package dev.uliana.task_management_system.task.service.impl;

import dev.uliana.task_management_system.task.dto.CreateTaskRequest;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.AdminTaskServiceInterface;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import dev.uliana.task_management_system.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AdminTaskService implements AdminTaskServiceInterface {
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserFinder userFinder;

    private final TaskParser taskParser;

    @Transactional
    public TaskResponse addTask(CreateTaskRequest request) {
        Task task = taskMapper.toTask(request, userFinder, taskParser);
        task.setComments(new ArrayList<>());
        task = taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    @Transactional
    public void deleteTask(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException(String.format("Задача с id %d не найдена", id));
        }
    }
}
package dev.uliana.task_management_system.task.service.impl;

import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.repository.TaskSpecification;
import dev.uliana.task_management_system.task.service.AdminSearchServiceInterface;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSearchService implements AdminSearchServiceInterface {
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByAssigneeId(
        long userId,
        String title,
        String status,
        String priority,
        int page,
        int limit) {
        Specification<Task> specification = getTasksBySpecification(title, status, priority);
        specification = specification.and(TaskSpecification.filterByAssigneeId(userId));

        return taskRepository.findAll(specification, PageRequest.of(page, limit))
            .stream()
            .map(taskMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByAuthorId(
        long userId,
        String title,
        String status,
        String priority,
        int page,
        int limit) {
        Specification<Task> specification = getTasksBySpecification(title, status, priority);
        specification = specification.and(TaskSpecification.filterByAuthorId(userId));

        return taskRepository.findAll(specification, PageRequest.of(page, limit))
            .stream()
            .map(taskMapper::toResponse)
            .toList();
    }

    private Specification<Task> getTasksBySpecification(String title, String status, String priority) {
        Specification<Task> specification = Specification.where(null);

        if (title != null && !title.isBlank()) {
            specification = specification.and(TaskSpecification.filterByTitle(title));
        }

        if (status != null && !status.isBlank()) {
            specification = specification.and(TaskSpecification.filterByStatus(status));
        }

        if (priority != null && !priority.isBlank()) {
            specification = specification.and(TaskSpecification.filterByPriority(priority));
        }

        return specification;
    }
}
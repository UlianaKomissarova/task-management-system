package dev.uliana.task_management_system.task;

import dev.uliana.task_management_system.TaskData;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.impl.AdminSearchService;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminSearchServiceTest {
    @InjectMocks
    private AdminSearchService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getTasksByAssigneeIdSuccess() {
        long userId = 1L;
        String title = "На";
        String status = "PENDING";
        String priority = "HIGH";
        int page = 0;
        int limit = 10;

        Task task = TaskData.getTask();
        TaskResponse response = TaskData.getResponse();
        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(any(Specification.class), ArgumentMatchers.eq(PageRequest.of(page, limit))))
            .thenReturn(taskPage);
        when(taskMapper.toResponse(task)).thenReturn(response);

        List<TaskResponse> result = taskService.getTasksByAssigneeId(userId, title, status, priority, page, limit);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    public void getTasksByAuthorIdSuccess() {
        long userId = 1L;
        String title = "На";
        String status = "PENDING";
        String priority = "HIGH";
        int page = 0;
        int limit = 10;

        Task task = TaskData.getTask();
        TaskResponse response = TaskData.getResponse();
        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(any(Specification.class), ArgumentMatchers.eq(PageRequest.of(page, limit))))
            .thenReturn(taskPage);
        when(taskMapper.toResponse(task)).thenReturn(response);

        List<TaskResponse> result = taskService.getTasksByAuthorId(userId, title, status, priority, page, limit);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }
}
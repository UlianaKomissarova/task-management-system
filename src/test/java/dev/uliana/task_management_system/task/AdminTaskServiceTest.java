package dev.uliana.task_management_system.task;

import dev.uliana.task_management_system.TaskData;
import dev.uliana.task_management_system.task.dto.CreateTaskRequest;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.task.service.impl.AdminTaskService;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import dev.uliana.task_management_system.user.service.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminTaskServiceTest {
    @InjectMocks
    private AdminTaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserFinder userFinder;

    @Mock
    private TaskParser taskParser;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    public void addTaskSuccess() {
        CreateTaskRequest request = TaskData.getCreationTaskRequest();
        Task task = TaskData.getTask();
        TaskResponse expectedResponse = TaskData.getResponse();

        when(taskMapper.toTask(request, userFinder, taskParser)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(expectedResponse);

        TaskResponse actualResponse = taskService.addTask(request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(taskMapper).toTask(request, userFinder, taskParser);
        verify(taskRepository).save(task);
        verify(taskMapper).toResponse(task);
    }

    @Test
    public void deleteTaskSuccess() {
        Task task = TaskData.getTask();
        long taskId = task.getId();

        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        assertDoesNotThrow(() -> taskService.deleteTask(taskId));
    }

    @Test
    public void deleteTaskNotFound() {
        long nonExistingTaskId = 1000L;

        when(taskRepository.existsById(nonExistingTaskId)).thenReturn(false);

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
            () -> taskService.deleteTask(nonExistingTaskId)
        );

        assertEquals(String.format("Задача с id %d не найдена", nonExistingTaskId), exception.getMessage());
        verify(taskRepository, never()).delete(any(Task.class));
    }
}
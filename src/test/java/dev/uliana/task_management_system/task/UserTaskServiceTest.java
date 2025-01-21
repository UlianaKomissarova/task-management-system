package dev.uliana.task_management_system.task;

import dev.uliana.task_management_system.TaskData;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.exception.InvalidTaskStatusException;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.model.TaskStatus;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.task.service.impl.UserTaskService;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import dev.uliana.task_management_system.task.service.validator.PermissionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTaskServiceTest {
    @InjectMocks
    private UserTaskService taskService;

    @Mock
    private TaskParser taskParser;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @Spy
    private PermissionValidator permissionValidator;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    public void updateTaskStatusSuccess() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user@mail.ru");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Task task = TaskData.getTask();
        String newStatus = TaskStatus.DONE.toString();
        String currentUserEmail = "user@mail.ru";
        TaskResponse expectedResponse = TaskData.getResponse();
        expectedResponse.setStatus(TaskStatus.valueOf(newStatus));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(expectedResponse);
        when(taskParser.parseTaskStatus(newStatus)).thenReturn(TaskStatus.DONE);
        doNothing().when(permissionValidator).validateAssigneeAccess(task, currentUserEmail);

        TaskResponse actualResponse = taskService.updateTaskStatus(task.getId(), newStatus);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
        verify(taskMapper).toResponse(task);
        verify(taskParser).parseTaskStatus(newStatus);
    }

    @Test
    public void updateTaskStatusTaskNotFound() {
        long nonExistingId = 1000L;
        String newStatus = TaskStatus.DONE.toString();

        when(taskRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskStatus(nonExistingId, newStatus));
    }

    @Test
    public void updateTaskStatusAccessDenied() {
        Task task = TaskData.getTask();
        task.getAssignee().setEmail("diff@mail.ru");
        String newStatus = TaskStatus.DONE.toString();
        String currentUserEmail = "user@mail.ru";

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(currentUserEmail);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        AccessDeniedException exception = assertThrows(AccessDeniedException.class,
            () -> taskService.updateTaskStatus(task.getId(), newStatus)
        );

        assertEquals("Пользователь может изменить статус только своей задачи", exception.getMessage());
    }

    @Test
    public void updateTaskStatusInvalidTaskStatus() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user@mail.ru");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Task task = TaskData.getTask();
        String invalidStatus = "INVALID_STATUS";
        String currentUserEmail = "user@mail.ru";

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(permissionValidator).validateAssigneeAccess(task, currentUserEmail);
        when(taskParser.parseTaskStatus(invalidStatus)).thenThrow(
            new InvalidTaskStatusException(String.format("Невалидный статус: %s", invalidStatus))
        );

        InvalidTaskStatusException exception = assertThrows(InvalidTaskStatusException.class,
            () -> taskService.updateTaskStatus(task.getId(), invalidStatus)
        );

        assertEquals(String.format("Невалидный статус: %s", invalidStatus), exception.getMessage());
    }
}
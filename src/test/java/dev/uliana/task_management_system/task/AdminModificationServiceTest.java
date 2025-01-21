package dev.uliana.task_management_system.task;

import dev.uliana.task_management_system.TaskData;
import dev.uliana.task_management_system.UserData;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.dto.UpdateTaskRequest;
import dev.uliana.task_management_system.task.exception.InvalidTaskPriorityException;
import dev.uliana.task_management_system.task.exception.InvalidTaskStatusException;
import dev.uliana.task_management_system.task.exception.TaskBadRequestException;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.model.TaskPriority;
import dev.uliana.task_management_system.task.model.TaskStatus;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.task.service.impl.AdminModificationService;
import dev.uliana.task_management_system.task.service.mapper.TaskMapper;
import dev.uliana.task_management_system.user.exception.UserNotFoundException;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.service.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminModificationServiceTest {
    @InjectMocks
    private AdminModificationService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskParser taskParser;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserFinder userFinder;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    public void addAssigneeSuccess() {
        Task task = TaskData.getTask();
        long taskId = task.getId();
        TaskResponse expectedResponse = TaskData.getResponse();
        expectedResponse.setAssigneeId(2L);
        User user = UserData.getUpdatedAssignee();
        String updatedEmail = user.getEmail();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(expectedResponse);
        when(userFinder.getUserByEmail(updatedEmail)).thenReturn(user);

        TaskResponse actualResponse = taskService.addAssignee(taskId, updatedEmail);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(task);
        verify(taskMapper).toResponse(task);
        verify(userFinder).getUserByEmail(updatedEmail);
    }

    @Test
    public void addAssigneeTaskNotFound() {
        User user = UserData.getUpdatedAssignee();
        String updatedEmail = user.getEmail();
        long nonExistingTaskId = 1000L;

        when(taskRepository.findById(nonExistingTaskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
            () -> taskService.addAssignee(nonExistingTaskId, updatedEmail)
        );

        assertEquals(String.format("Задача с id %d не найдена", nonExistingTaskId), exception.getMessage());
    }

    @Test
    public void addAssigneeTaskBadRequest() {
        Task task = TaskData.getTask();
        long taskId = task.getId();
        User user = task.getAssignee();
        String currentEmail = user.getEmail();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskBadRequestException exception = assertThrows(TaskBadRequestException.class,
            () -> taskService.addAssignee(taskId, currentEmail)
        );

        assertEquals(String.format("Пользователь %s уже является исполнителем задачи", currentEmail), exception.getMessage());
    }

    @Test
    public void updateTaskSuccess() {
        UpdateTaskRequest request = TaskData.getUpdateTaskRequest();
        Task task = TaskData.getTask();
        long taskId = task.getId();
        User user = task.getAuthor();
        String email = request.getAuthorEmail();

        String taskStatus = request.getStatus();
        String taskPriority = request.getPriority();
        TaskResponse expectedResponse = TaskData.getUpdatedResponse();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateTaskFromRequest(request, task);
        when(taskParser.parseTaskStatus(taskStatus)).thenReturn(TaskStatus.DONE);
        when(taskParser.parseTaskPriority(taskPriority)).thenReturn(TaskPriority.LOW);
        when(userFinder.getUserByEmail(email)).thenReturn(user);
        when(taskMapper.toResponse(task)).thenReturn(expectedResponse);
        when(taskRepository.save(task)).thenReturn(task);

        TaskResponse actualResponse = taskService.updateTask(taskId, request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updateTaskNotFound() {
        UpdateTaskRequest request = TaskData.getUpdateTaskRequest();
        long nonExistingTaskId = 1000L;

        when(taskRepository.findById(nonExistingTaskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
            () -> taskService.updateTask(nonExistingTaskId, request)
        );

        assertEquals(String.format("Задача с id %d не найдена", nonExistingTaskId), exception.getMessage());
    }

    @Test
    public void updateTaskInvalidTaskPriority() {
        UpdateTaskRequest request = TaskData.getUpdateTaskRequest();
        String invalidPriority = "INVALID_PRIORITY";
        request.setPriority(invalidPriority);

        Task task = TaskData.getTask();
        long taskId = task.getId();
        String taskStatus = request.getStatus();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateTaskFromRequest(request, task);
        when(taskParser.parseTaskStatus(taskStatus)).thenReturn(TaskStatus.DONE);
        when(taskParser.parseTaskPriority(invalidPriority)).thenThrow(
            new InvalidTaskPriorityException(String.format("Невалидный приоритет: %s", invalidPriority))
        );

        InvalidTaskPriorityException exception = assertThrows(InvalidTaskPriorityException.class,
            () -> taskService.updateTask(taskId, request)
        );

        assertEquals(String.format("Невалидный приоритет: %s", invalidPriority), exception.getMessage());
    }

    @Test
    public void updateTaskInvalidTaskStatus() {
        UpdateTaskRequest request = TaskData.getUpdateTaskRequest();
        String invalidStatus = "INVALID_STATUS";
        request.setStatus(invalidStatus);

        Task task = TaskData.getTask();
        long taskId = task.getId();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateTaskFromRequest(request, task);
        when(taskParser.parseTaskStatus(invalidStatus)).thenThrow(
            new InvalidTaskStatusException(String.format("Невалидный статус: %s", invalidStatus))
        );

        InvalidTaskStatusException exception = assertThrows(InvalidTaskStatusException.class,
            () -> taskService.updateTask(taskId, request)
        );

        assertEquals(String.format("Невалидный статус: %s", invalidStatus), exception.getMessage());
    }

    @Test
    public void updateTaskUserNotFound() {
        UpdateTaskRequest request = TaskData.getUpdateTaskRequest();
        String nonExistingEmail = "fake@mail.ru";
        request.setAuthorEmail(nonExistingEmail);

        Task task = TaskData.getTask();
        long taskId = task.getId();
        String taskStatus = request.getStatus();
        String taskPriority = request.getPriority();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateTaskFromRequest(request, task);
        when(taskParser.parseTaskStatus(taskStatus)).thenReturn(TaskStatus.DONE);
        when(taskParser.parseTaskPriority(taskPriority)).thenReturn(TaskPriority.LOW);
        when(userFinder.getUserByEmail(nonExistingEmail)).thenThrow(
            new UserNotFoundException(String.format("Пользователь с эл.почтой %s не найден", nonExistingEmail))
        );

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
            () -> taskService.updateTask(taskId, request)
        );

        assertEquals(String.format("Пользователь с эл.почтой %s не найден", nonExistingEmail), exception.getMessage());
    }
}
package dev.uliana.task_management_system.comment;

import dev.uliana.task_management_system.CommentData;
import dev.uliana.task_management_system.TaskData;
import dev.uliana.task_management_system.comment.dto.CommentRequest;
import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.comment.model.Comment;
import dev.uliana.task_management_system.comment.repository.CommentRepository;
import dev.uliana.task_management_system.comment.service.CommentService;
import dev.uliana.task_management_system.comment.service.mapper.CommentMapper;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.user.exception.UserNotFoundException;
import dev.uliana.task_management_system.user.service.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private UserFinder userFinder;

    @BeforeEach
    public void setUp() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Test
    public void testAddCommentSuccess() {
        CommentRequest request = CommentData.getCommentRequest();
        Comment comment = CommentData.getComment();
        CommentResponse expectedResponse = CommentData.getCommentResponse();
        Task task = comment.getTask();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentMapper.toComment(request, userFinder)).thenReturn(comment);
        when(commentMapper.toResponse(comment)).thenReturn(expectedResponse);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(taskRepository.save(task)).thenReturn(task);

        CommentResponse actualResponse = commentService.addComment(1L, request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(taskRepository).save(task);
        verify(taskRepository).findById(1L);
        verify(commentRepository).save(comment);
        verify(commentMapper).toComment(request, userFinder);
        verify(commentMapper).toResponse(comment);
    }

    @Test
    public void testAddCommentUserNotFound() {
        CommentRequest request = CommentData.getCommentRequest();
        Task task = TaskData.getTask();
        long nonExistingId = 1000L;
        request.setAuthorId(nonExistingId);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userFinder.getUserById(nonExistingId)).thenThrow(
            new UserNotFoundException(String.format("Пользователь с id %d не найден", nonExistingId))
        );

        assertThrows(UserNotFoundException.class, () -> {
            commentService.addComment(1L, request);
        });
    }

    @Test
    public void testAddCommentTaskNotFound() {
        CommentRequest request = CommentData.getCommentRequest();
        long nonExistingId = 1000L;

        when(taskRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            commentService.addComment(nonExistingId, request);
        });
    }
}
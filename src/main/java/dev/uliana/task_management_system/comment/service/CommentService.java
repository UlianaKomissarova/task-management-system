package dev.uliana.task_management_system.comment.service;

import dev.uliana.task_management_system.comment.dto.CommentRequest;
import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.comment.model.Comment;
import dev.uliana.task_management_system.comment.repository.CommentRepository;
import dev.uliana.task_management_system.comment.service.mapper.CommentMapper;
import dev.uliana.task_management_system.task.exception.TaskNotFoundException;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.repository.TaskRepository;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface {
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final UserFinder userFinder;

    private final TaskRepository taskRepository;

    @Transactional
    public CommentResponse addComment(Long taskId, CommentRequest request) {
        Task task = taskRepository.findById(taskId).orElseThrow(
            () -> new TaskNotFoundException(String.format("Задача с id %d не найдена", taskId))
        );

        userFinder.getUserById(request.getAuthorId());

        Comment addedComment = commentMapper.toComment(request, userFinder);
        addedComment.setTask(task);
        addedComment = commentRepository.save(addedComment);
        updateTaskComments(task, addedComment);

        return commentMapper.toResponse(addedComment);
    }

    private void updateTaskComments(Task task, Comment addedComment) {
        List<Comment> comments = task.getComments();
        comments.add(addedComment);
        task.setComments(comments);
        taskRepository.save(task);
    }
}
package dev.uliana.task_management_system.comment.service;

import dev.uliana.task_management_system.comment.dto.CommentRequest;
import dev.uliana.task_management_system.comment.dto.CommentResponse;

public interface CommentServiceInterface {
    CommentResponse addComment(Long taskId, CommentRequest request);
}
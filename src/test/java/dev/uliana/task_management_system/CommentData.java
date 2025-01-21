package dev.uliana.task_management_system;

import dev.uliana.task_management_system.comment.dto.CommentRequest;
import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.comment.model.Comment;

public class CommentData {
    public static CommentRequest getCommentRequest() {
        return new CommentRequest("Комментарий к задаче", 1L);
    }

    public static Comment getComment() {
        return new Comment(1L, "Комментарий к задаче", UserData.getAuthor(), TaskData.getTask());
    }

    public static CommentResponse getCommentResponse() {
        return new CommentResponse(1L, "Комментарий к задаче", 1L, 1L);
    }
}
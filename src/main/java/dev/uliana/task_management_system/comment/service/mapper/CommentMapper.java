package dev.uliana.task_management_system.comment.service.mapper;

import dev.uliana.task_management_system.comment.dto.CommentRequest;
import dev.uliana.task_management_system.comment.dto.CommentResponse;
import dev.uliana.task_management_system.comment.model.Comment;
import dev.uliana.task_management_system.user.model.User;
import dev.uliana.task_management_system.user.service.UserFinder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentResponse toResponse(Comment comment);

    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthorIdToUser")
    Comment toComment(CommentRequest request, @Context UserFinder userFinder);

    @Named("mapAuthorIdToUser")
    default User mapAuthorIdToUser(Long authorId, @Context UserFinder userFinder) {
        return userFinder.getUserById(authorId);
    }
}
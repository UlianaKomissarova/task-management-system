package dev.uliana.task_management_system.task.service.mapper;

import dev.uliana.task_management_system.comment.service.mapper.CommentMapper;
import dev.uliana.task_management_system.task.dto.CreateTaskRequest;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.dto.UpdateTaskRequest;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.service.TaskParser;
import dev.uliana.task_management_system.user.service.UserFinder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = CommentMapper.class)
public interface TaskMapper {
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    TaskResponse toResponse(Task task);

    @Mapping(target = "author", expression = "java(userFinder.getUserById(request.getAuthorId()))")
    @Mapping(target = "assignee", expression = "java(userFinder.getUserById(request.getAssigneeId()))")
    @Mapping(target = "status", expression = "java(taskParser.parseTaskStatus(request.getStatus()))")
    @Mapping(target = "priority", expression = "java(taskParser.parseTaskPriority(request.getPriority()))")
    Task toTask(CreateTaskRequest request, @Context UserFinder userFinder, @Context TaskParser taskParser);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "author", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromRequest(UpdateTaskRequest request, @MappingTarget Task task);
}
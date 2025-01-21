package dev.uliana.task_management_system;

import dev.uliana.task_management_system.task.dto.CreateTaskRequest;
import dev.uliana.task_management_system.task.dto.TaskResponse;
import dev.uliana.task_management_system.task.dto.UpdateTaskRequest;
import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.model.TaskPriority;
import dev.uliana.task_management_system.task.model.TaskStatus;

import java.util.ArrayList;

public class TaskData {
    public static Task getTask() {
        return new Task(
            1L,
            "Название",
            "Описание",
            TaskStatus.PENDING,
            TaskPriority.HIGH,
            UserData.getAuthor(),
            UserData.getAssignee(),
            new ArrayList<>()
        );
    }

    public static TaskResponse getResponse() {
        return new TaskResponse(
            1L,
            "Название",
            "Описание",
            TaskStatus.PENDING,
            TaskPriority.HIGH,
            UserData.getAuthor().getId(),
            UserData.getAssignee().getId(),
            new ArrayList<>()
        );
    }

    public static CreateTaskRequest getCreationTaskRequest() {
        return new CreateTaskRequest(
            "Название",
            "Описание",
            "PENDING",
            "HIGH",
            UserData.getAuthor().getId(),
            UserData.getAssignee().getId()
        );
    }

    public static UpdateTaskRequest getUpdateTaskRequest() {
        return new UpdateTaskRequest(
            "Новое название",
            "Новое описание",
            "DONE",
            "LOW",
            "new@mail.ru",
            "upd@mail.ru"
        );
    }

    public static TaskResponse getUpdatedResponse() {
        return new TaskResponse(
            1L,
            "Новое название",
            "Новое описание",
            TaskStatus.DONE,
            TaskPriority.LOW,
            UserData.getAuthor().getId(),
            UserData.getAssignee().getId(),
            new ArrayList<>()
        );
    }
}
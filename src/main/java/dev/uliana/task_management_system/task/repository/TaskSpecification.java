package dev.uliana.task_management_system.task.repository;

import dev.uliana.task_management_system.task.model.Task;
import dev.uliana.task_management_system.task.model.TaskPriority;
import dev.uliana.task_management_system.task.model.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public static Specification<Task> filterByTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"), title);
    }

    public static Specification<Task> filterByStatus(String status) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("status"), TaskStatus.valueOf(status.toUpperCase()));
    }

    public static Specification<Task> filterByPriority(String priority) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("priority"), TaskPriority.valueOf(priority.toUpperCase()));
    }

    public static Specification<Task> filterByAssigneeId(long assigneeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
    }

    public static Specification<Task> filterByAuthorId(long authorId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }
}
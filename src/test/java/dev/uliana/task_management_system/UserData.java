package dev.uliana.task_management_system;

import dev.uliana.task_management_system.user.dto.SignInRequest;
import dev.uliana.task_management_system.user.dto.SignUpRequest;
import dev.uliana.task_management_system.user.model.Role;
import dev.uliana.task_management_system.user.model.User;

public class UserData {
    public static User getAuthor() {
        return new User(1L, "author", "author@mail.ru", "Password@123", Role.ROLE_USER, true);
    }

    public static User getAssignee() {
        return new User(1L, "assignee", "assignee@mail.ru", "Password@123", Role.ROLE_USER, true);
    }

    public static User getUpdatedAssignee() {
        return new User(2L, "assignee2", "assignee2@mail.ru", "Password@123", Role.ROLE_USER, true);
    }

    public static SignInRequest getSignInRequest() {
        return new SignInRequest("author@mail.ru", "Password@123");
    }

    public static SignUpRequest getSignUpRequest() {
        return new SignUpRequest("author", "author@mail.ru", "Password@123", Role.ROLE_USER);
    }
}
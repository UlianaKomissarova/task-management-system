package dev.uliana.task_management_system.shared;

public class Constants {
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,99}$";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String USER_SERVICE_PACKAGE_PATH = "execution(* dev.uliana.task_management_system.user.service.*.*(..))";

    public static final String TASK_SERVICE_PACKAGE_PATH = "execution(* dev.uliana.task_management_system.task.service.*.*(..))";

    public static final String COMMENT_SERVICE_PACKAGE_PATH = "execution(* dev.uliana.task_management_system.comment.service.*.*(..))";

    public static final String SECURITY_SERVICE_PACKAGE_PATH = "execution(* dev.uliana.task_management_system.security.service.*.*(..))";

    public static final String HEADER = "Authorization";

    public static final String BEARER = "Bearer ";
}
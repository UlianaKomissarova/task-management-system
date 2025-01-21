package dev.uliana.task_management_system.shared.exception;

public class ErrorMessage {
    public static final String USERNAME_IS_BLANK = "Укажите логин пользователя";

    public static final String USERNAME_PATTERN_VIOLATION = "Логин может включать в себя латинские буквы, цифры, символы.";

    public static final String EMAIL_IS_BLANK = "Укажите адрес эл.почты";

    public static final String EMAIL_PATTERN_VIOLATION = "Невалидная эл.почта";

    public static final String PASSWORD_IS_BLANK = "Укажите пароль пользователя";

    public static final String PASSWORD_PATTERN_VIOLATION = "Пароль должен содержать от 8 до 20 символов, включая: " +
        "хотя бы 1 строчную букву, хотя бы 1 заглавную букву, как минимум 1 цифру и как минимум 1 спец.символ.";

    public static final String ROLE_IS_NULL = "Укажите роль пользователя";

    public static final String COMMENT_TASK_ID_IS_NULL = "Укажите id задачи для комментария";

    public static final String COMMENT_TEXT_IS_BLANK = "Напишите текст комментария";

    public static final String AUTHOR_ID_IS_NULL = "Укажите id автора";

    public static final String ASSIGNEE_ID_IS_NULL = "Укажите id исполнителя";

    public static final String TITLE_IS_BLANK = "Укажите название задачи";

    public static final String STATUS_IS_BLANK = "Укажите статус выполнения задачи: PENDING, IN_PROCESS, DONE";

    public static final String PRIORITY_IS_BLANK = "Укажите приоритет задачи: LOW, MEDIUM, HIGH";

    public static final String DESCRIPTION_IS_BLANK = "Заполните описание задачи";
}
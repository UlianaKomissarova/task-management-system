package dev.uliana.task_management_system.shared;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static dev.uliana.task_management_system.shared.Constants.COMMENT_SERVICE_PACKAGE_PATH;
import static dev.uliana.task_management_system.shared.Constants.SECURITY_SERVICE_PACKAGE_PATH;
import static dev.uliana.task_management_system.shared.Constants.TASK_SERVICE_PACKAGE_PATH;
import static dev.uliana.task_management_system.shared.Constants.USER_SERVICE_PACKAGE_PATH;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before(TASK_SERVICE_PACKAGE_PATH + " || " + USER_SERVICE_PACKAGE_PATH + " || " +
        COMMENT_SERVICE_PACKAGE_PATH + " || " + SECURITY_SERVICE_PACKAGE_PATH)
    public void logBefore(JoinPoint joinPoint) {
        log.info("Метод {} начал свое выполнение", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = (TASK_SERVICE_PACKAGE_PATH + " || " + USER_SERVICE_PACKAGE_PATH + " || " +
        COMMENT_SERVICE_PACKAGE_PATH + " || " + SECURITY_SERVICE_PACKAGE_PATH),
        returning = "returnedValue")
    public void logAround(JoinPoint joinPoint, Object returnedValue) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.debug("Метод {} с параметрами {} выполняется...", methodName, Arrays.asList(args));
        log.debug("Метод выполнен успешно и вернул значение: {}", returnedValue);
    }

    @AfterThrowing(pointcut = (TASK_SERVICE_PACKAGE_PATH + " || " + USER_SERVICE_PACKAGE_PATH + " || " +
        COMMENT_SERVICE_PACKAGE_PATH + " || " + SECURITY_SERVICE_PACKAGE_PATH),
        throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Исключение в {}.{}() с причиной = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            exception.getCause() != null ? exception.getCause() : "NULL"
        );
    }
}
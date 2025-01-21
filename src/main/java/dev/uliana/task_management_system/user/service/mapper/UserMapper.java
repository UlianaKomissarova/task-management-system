package dev.uliana.task_management_system.user.service.mapper;

import dev.uliana.task_management_system.user.dto.SignUpRequest;
import dev.uliana.task_management_system.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.password()))")
    User toUser(SignUpRequest request, PasswordEncoder passwordEncoder);
}
package com.example.lease_by.mapper;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.model.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component
@Mapper(
        componentModel = SPRING,
        imports = {Optional.class, StringUtils.class}
)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "profile", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    public abstract User updateUser(UserCreateDto dto,
                                    @MappingTarget User user);

    @Mapping(target = "profileReadDto", source = "profile")
    public abstract UserReadDto mapToUserReadDto(User entity);

    @Mapping(target = "profile", source = "profileReadDto")
    public abstract User mapToUser(UserReadDto dto);

    @Mapping(target = "password",
            expression = """
                     java(encodePassword(dto))
                    """)
    public abstract User mapToUser(UserCreateDto dto);

    protected String encodePassword(UserCreateDto dto) {
        return Optional.ofNullable(dto.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Password not found!"));
    }
}

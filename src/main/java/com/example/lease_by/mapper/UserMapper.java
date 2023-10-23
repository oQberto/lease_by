package com.example.lease_by.mapper;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.model.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

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
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User updateUser(UserCreateDto dto,
                                    @MappingTarget User user);

    public abstract UserReadDto mapToUserReadDto(User entity);

    public abstract User mapToUser(UserReadDto dto);

    @Mapping(target = "password",
            expression = """
                    java(
                    Optional.ofNullable(dto.getPassword())
                                    .filter(StringUtils::hasText)
                                    .map(passwordEncoder::encode)
                                    .get())
                    """)
    public abstract User mapToUser(UserCreateDto dto);
}

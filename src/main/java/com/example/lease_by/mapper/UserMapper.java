package com.example.lease_by.mapper;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

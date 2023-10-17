package com.example.lease_by.mapper;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.model.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
@Component
public interface UserMapper {

    UserReadDto mapToUserReadDto(User entity);

    User mapToUser(UserReadDto dto);

    User mapToUser(UserCreateDto dto);
}

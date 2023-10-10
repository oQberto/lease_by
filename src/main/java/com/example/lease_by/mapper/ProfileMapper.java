package com.example.lease_by.mapper;

import com.example.lease_by.dto.ProfileReadDto;
import com.example.lease_by.model.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        injectionStrategy = CONSTRUCTOR,
        uses = UserReadMapper.class
)
public interface ProfileMapper {

    @Mapping(target = "userReadDto", source = "user")
    ProfileReadDto mapToProfileReadDto(Profile entity);

    @Mapping(target = "user", source = "userReadDto")
    Profile mapToProfile(ProfileReadDto dto);
}

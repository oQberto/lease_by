package com.example.lease_by.mapper;

import com.example.lease_by.dto.ProfileCreateDto;
import com.example.lease_by.dto.ProfileReadDto;
import com.example.lease_by.model.entity.Profile;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        injectionStrategy = CONSTRUCTOR,
        uses = UserMapper.class
)
@Component
public interface ProfileMapper {

    @Mapping(target = "userReadDto", source = "user")
    ProfileReadDto mapToProfileReadDto(Profile entity);

    @Mapping(target = "user", source = "userReadDto")
    Profile mapToProfile(ProfileReadDto dto);

    Profile mapToProfile(ProfileCreateDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Profile updateProfile(ProfileCreateDto dto,
                          @MappingTarget Profile profile);
}

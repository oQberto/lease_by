package com.example.lease_by.mapper;

import com.example.lease_by.dto.AboutDto;
import com.example.lease_by.model.entity.About;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AboutMapper {

    AboutDto mapToAboutDto(About entity);

    About mapToAbout(AboutDto dto);
}

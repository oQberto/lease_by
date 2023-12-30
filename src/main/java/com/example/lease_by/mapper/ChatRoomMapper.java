package com.example.lease_by.mapper;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.model.entity.ChatRoom;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@Mapper(componentModel = SPRING)
public interface ChatRoomMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChatRoomDto mapToChatRoomDto(ChatRoom chatRoom);
}

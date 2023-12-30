package com.example.lease_by.dto.chat;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.model.entity.ChatMessage;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ChatRoomDto {
    UserReadDto sender;
    UserReadDto recipient;

    @Builder.Default
    List<ChatMessage> messages = new ArrayList<>();
}

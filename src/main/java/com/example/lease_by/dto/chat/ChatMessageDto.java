package com.example.lease_by.dto.chat;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ChatMessageDto {
    Long id;
    String content;
    LocalDateTime sendTime;
    Long recipientId;
    Long senderId;
    Long chatRoomId;
}

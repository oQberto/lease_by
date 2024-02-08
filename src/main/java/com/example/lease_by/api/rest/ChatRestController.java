package com.example.lease_by.api.rest;

import com.example.lease_by.dto.chat.ChatMessageDto;
import com.example.lease_by.dto.chat.ChatNotification;
import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.model.entity.enums.ChatRoomStatus;
import com.example.lease_by.service.ChatMessageService;
import com.example.lease_by.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chats")
public class ChatRestController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessageDto) {
        ChatMessageDto savedMassage = chatMessageService.saveMassage(chatMessageDto);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDto.getRecipientId()),
                "/queue/messages",
                ChatNotification.builder()
                        .senderId(savedMassage.getSenderId())
                        .recipientId(savedMassage.getRecipientId())
                        .content(savedMassage.getContent())
                        .build()
        );
    }

    @GetMapping("/{senderId}")
    public ResponseEntity<List<ChatRoomDto>> getChatsBySenderId(@PathVariable("senderId") Long senderId) {
        return ResponseEntity.ok(
                chatRoomService.getChatRoomsBySenderId(senderId)
        );
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<ChatRoomDto> getChat(@PathVariable("senderId") Long senderId,
                                               @PathVariable("recipientId") Long recipientId) {
        return ResponseEntity.ok(
                chatRoomService.getChatBy(senderId, recipientId)
        );
    }

    @PatchMapping("/update/{id}/{status}")
    public ResponseEntity<ChatRoomDto> updateChatRoomStatus(@PathVariable("id") Long id,
                                                            @PathVariable("status") ChatRoomStatus status) {
        return ResponseEntity.ok(
                chatRoomService.updateChatRoomStatus(id, status)
        );
    }
}

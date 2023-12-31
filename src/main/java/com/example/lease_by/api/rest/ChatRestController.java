package com.example.lease_by.api.rest;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.model.entity.enums.ChatRoomStatus;
import com.example.lease_by.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chats")
public class ChatRestController {
    private final ChatRoomService chatRoomService;

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

package com.example.lease_by.api.rest;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

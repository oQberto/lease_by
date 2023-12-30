package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findAllBySenderId(Long senderId);

    ChatRoom findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}

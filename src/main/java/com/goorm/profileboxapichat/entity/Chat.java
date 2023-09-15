package com.goorm.profileboxapichat.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat")
public class Chat {
    @Id
    private String id;
    private String chatRoomId;
    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime createDt;
}

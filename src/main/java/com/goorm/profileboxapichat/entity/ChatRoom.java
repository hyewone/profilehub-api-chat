package com.goorm.profileboxapichat.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "chatRoom")
public class ChatRoom {
    @Id
    private String id;
    private List<String> attendeeIdList;
    private String title;
    private LocalDateTime createDt;
}

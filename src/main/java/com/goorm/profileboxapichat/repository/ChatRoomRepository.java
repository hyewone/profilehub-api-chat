package com.goorm.profileboxapichat.repository;

import com.goorm.profileboxapichat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom, String> {

    @Tailable
    @Query("{'attendeeIdList': ?0}")
    Flux<ChatRoom> findChatRoomByMemberId(String memberId);

}

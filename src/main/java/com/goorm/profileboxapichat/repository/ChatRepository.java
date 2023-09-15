package com.goorm.profileboxapichat.repository;

import com.goorm.profileboxapichat.entity.Chat;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {


    @Tailable
    @Query("{chatRoomId : ?0}")
//    @Query("{id : ?0, senderId:  {$ne :  ?1 }}")
    Flux<Chat> findByChatRoomIdAndNotSenderId(String chatRoomId, String senderId);

}

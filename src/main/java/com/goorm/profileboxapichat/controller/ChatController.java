package com.goorm.profileboxapichat.controller;

import com.goorm.profileboxapichat.entity.Chat;
import com.goorm.profileboxapichat.entity.ChatRoom;
import com.goorm.profileboxapichat.entity.Member;
import com.goorm.profileboxapichat.repository.ChatRepository;
import com.goorm.profileboxapichat.repository.ChatRoomRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Chat")
@RestController
@RequestMapping(("/v1/chat"))
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
public class ChatController {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Operation(summary = "채팅 룸 조회")
    @GetMapping(path = "/rooms")
    public Flux<ChatRoom> getRooms(Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        return chatRoomRepository.findChatRoomByMemberId(member.getMemberId().toString())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "채팅 룸 생성")
    @PostMapping("/room")
    public Mono<ChatRoom> addRoom(@RequestBody ChatRoom chatRoom, Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        List<String> attendeeList = chatRoom.getAttendeeIdList();
        attendeeList.add(member.getMemberId().toString());
        chatRoom.setAttendeeIdList(attendeeList);
        chatRoom.setCreateDt(LocalDateTime.now());
        return chatRoomRepository.save(chatRoom);
    }

    @Operation(summary = "채팅 조회")
    @GetMapping(path = "/message/{chatRoomId}")
    public Flux<Chat> receiveMessages(@PathVariable String chatRoomId, Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        return chatRepository.findByChatRoomIdAndNotSenderId(chatRoomId, member.getMemberId().toString())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "채팅 전송")
    @PostMapping("/message")
    public Mono<Chat> sendMessage(@RequestBody Chat chat, Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        chat.setCreateDt(LocalDateTime.now());
        chat.setSenderId(member.getMemberId().toString());
        return chatRepository.save(chat);
    }
}

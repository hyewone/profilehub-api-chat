package com.goorm.profileboxapichat.entity;

import com.goorm.profileboxapichat.enumeration.MemberType;
import com.goorm.profileboxapichat.enumeration.ProviderType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private Long memberId;

    private MemberType memberType; // ACTOR, PRODUCER, ADMIN

    private ProviderType providerType; // GOOGLE, KAKAO, NAVER

    private String memberEmail;

    private LocalDateTime createDt;

    private LocalDateTime modifyDt;
}

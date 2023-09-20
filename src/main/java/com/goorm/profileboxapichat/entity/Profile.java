package com.goorm.profileboxapichat.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private Long profileId;

    private String actorName;

    private String content;

    private String title;

    private String defaultImageId;

    private String ynType;

    private LocalDateTime createDt;

    private LocalDateTime modifyDt;

    private Member member;

    List<Object> imageEntities;

    List<Object> videoEntities;

    List<Object> filmoEntities;

    List<Object> linkEntities;

}

package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {
    private int reviewId;
    private int videoId;
    private String userId;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private boolean isDeleted;
}

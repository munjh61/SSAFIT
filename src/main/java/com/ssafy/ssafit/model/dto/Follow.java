package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    private int followID;
    private String followerId;
    private String followingId;
    private LocalDateTime createdAt;
}

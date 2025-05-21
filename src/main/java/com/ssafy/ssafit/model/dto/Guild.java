package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Guild {
    private int guildId;
    private String guildName;
    private String description;
    private String userId;
    private LocalDateTime regDate;
    private Long headCount;
}

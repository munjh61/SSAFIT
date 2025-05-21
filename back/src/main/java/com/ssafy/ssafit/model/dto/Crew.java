package com.ssafy.ssafit.model.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Crew {
    private long crewId;
    private String userId;
    private long guildId;
    private int status;
}

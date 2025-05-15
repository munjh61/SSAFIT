package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Emailtmp {
    private int emailtmpId;
    private String email;
    private String token;
    private LocalDateTime due;
}

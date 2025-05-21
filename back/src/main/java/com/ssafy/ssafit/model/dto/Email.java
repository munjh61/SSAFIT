package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private int emailId;
    private String address;
    private String token;
    private LocalDateTime due;
    private boolean verified;
}

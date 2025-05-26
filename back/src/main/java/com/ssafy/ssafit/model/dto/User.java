package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private LocalDateTime regDate;
    private String role;
    private boolean isDeleted;
    //
    private String statusMsg1;
    private String statusMsg2;
}

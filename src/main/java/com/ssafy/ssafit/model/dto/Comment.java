package com.ssafy.ssafit.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    private int commentId;
    private int boardId;
    private String userId;
    private String content;
    private String regDate;
    private String updateDate;
    private boolean isDeleted;
}

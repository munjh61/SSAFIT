package com.ssafy.ssafit.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
    private Long boardId;
    private String userId;
    private String title;
    private String content;
    private String regDate;
    private int viewCnt;
    private String tag;
    private boolean isDeleted;

}

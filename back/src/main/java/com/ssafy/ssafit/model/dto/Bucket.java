package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bucket {
    private long bucketId;
    private long boardId;
    private String userId;
    private int done;
    private LocalDateTime doneDate;
}

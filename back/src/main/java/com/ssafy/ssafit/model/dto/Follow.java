package com.ssafy.ssafit.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {
    private int followId;
    private String followerId;
    private String followingId;
    private LocalDateTime createdAt;
    // 이 아래 부분은 table에 없음. mapper에서 불러올 때 dto 새로 만들기 귀찮아서 여기에 씀
    private String followerName;
    private String followingName;

}

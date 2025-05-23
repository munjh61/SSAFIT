package com.ssafy.ssafit.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Img {
    private Long imgId;
    private Long boardId;
    private String title;
    private String orgName;
    private String name;
}

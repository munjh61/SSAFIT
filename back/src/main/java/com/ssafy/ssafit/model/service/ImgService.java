package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Img;

import java.util.List;

public interface ImgService {
//    List<Img> getImgByUserId(String userId);
    List<Img> getImgByUserId(Long boardId);
}

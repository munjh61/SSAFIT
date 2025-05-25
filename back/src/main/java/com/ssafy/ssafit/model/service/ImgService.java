package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Img;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgService {
//    List<Img> getImgByUserId(String userId);
    List<Img> getImgByBoardId(Long boardId);
}

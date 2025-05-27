package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Img;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ImgService {
//    List<Img> getImgByUserId(String userId);
    List<Img> getImgByBoardId(Long boardId);

    //수정
    void updateImg(Long boardId, String fileName);

    Map<Long, List<Img>> getImgListByBoardId(List<Long> boardIds);
}

package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Img;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface ImgDao {
//    List<Img> selectImgByUserId(String userId);
    List<Img> selectImgByBoardId(Long boardId);
    //이미지 저장
    void insertImg(Img img);
    //이미지 수정
    void updateImage(Long boardId, String fileName);
}

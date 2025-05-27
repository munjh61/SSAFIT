package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ImgDao {
    //해당 유저들이 작성한 게시글 목록 가져 올 때 사용
    List<Img> selectImgByBoardId(Long boardId);
    //이미지 저장
    void insertImg(Img img);
    //이미지 수정
    void updateImage(Long boardId, String fileName);

    List<Img> selectImgListByBoardId(@Param("boardIds") List<Long> boardIds);
}

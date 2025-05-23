package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Img;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface ImgDao {
//    List<Img> selectImgByUserId(String userId);
    List<Img> selectImgByUserId(Long boardId);
}

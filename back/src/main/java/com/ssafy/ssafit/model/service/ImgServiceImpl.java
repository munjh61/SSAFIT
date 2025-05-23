package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.ImgDao;
import com.ssafy.ssafit.model.dto.Img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService{

    private final ImgDao imgDao;

//    @Override
//    public List<Img> getImgByUserId(String userId) {
//        return imgDao.selectImgByUserId(userId);
//    }
    @Override
    public List<Img> getImgByUserId(Long boardId) {
        return imgDao.selectImgByUserId(boardId);
    }
}

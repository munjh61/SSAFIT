package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BoardDao;
import com.ssafy.ssafit.model.dao.ImgDao;
import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.dto.Img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService{

    private final ImgDao imgDao;
    private final BoardDao boardDao;

//    @Override
//    public List<Img> getImgByUserId(String userId) {
//        return imgDao.selectImgByUserId(userId);
//    }
    @Override
    public List<Img> getImgByBoardId(Long boardId) {
        return imgDao.selectImgByBoardId(boardId);
    }

    @Override
    public void updateImg(Long boardId, String fileName) {
        imgDao.updateImage(boardId, fileName);
    }

    @Override
    public Map<Long, List<Img>> getImgListByBoardId(List<Long> boardIds) {
        List<Img> allImages = imgDao.selectImgListByBoardId(boardIds);

        return allImages.stream().collect(Collectors.groupingBy(Img::getBoardId));
    }
}

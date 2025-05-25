package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BoardDao;
import com.ssafy.ssafit.model.dao.ImgDao;
import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.dto.Img;
import com.ssafy.ssafit.model.util.FileUploadUtil;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;
    private final ImgDao imgDao;
    @Autowired
    private FileUploadUtil fileUploadUtil;



    @Override
    public List<Board> searchBoard(String keyword) {
        return boardDao.searchByKeyword(keyword);
    }

    @Override
    public List<Board> getAllBoards() {
        return boardDao.selectAll();
    }

    @Override
    public List<Board> getBoardByUserId(String userId) {
        return boardDao.selectByUserId(userId);
    }

    @Override
    public Board getBoardByBoardId(long boardId) {
        return boardDao.selectByBoardId(boardId);
    }

    @Override
    public String getTitleByBoardId(long boardId) {
        return boardDao.selectTitleByBoardId(boardId);
    }

    @Override
    public String getTagByBoardId(long boardId) {
        return boardDao.selectTagByBoardId(boardId);
    }

    @Override
    public void createBoard(String content, MultipartFile image, String userId) {
        Board board = new Board();
        board.setUserId(userId);
        board.setContent(content);
        board.setRegDate(LocalDateTime.now());

        boardDao.insertBoard(board);
        //이미지 저장
        if (image != null && !image.isEmpty()) {
            String fileName = fileUploadUtil.save(image); // 예: 고유 파일명 생성 + 저장
            Img img = new Img();
            img.setBoardId(board.getBoardId()); // 방금 생성된 board PK
            img.setName(fileName);
            img.setOrgName(image.getOriginalFilename());
            imgDao.insertImg(img);
        }
    }

    @Override
    public boolean modifyBoard(String userId, Board board) {
        Board tmp = boardDao.selectByBoardId(board.getBoardId()); //수정 대상인 board

        //수정 대상인 board가 null 값이면, 실패
        if(tmp == null){
            return false;
        }
        //해당 board의 작성자가 현재 로그인된 유저와 같은지 확인
        if(!tmp.getUserId().equals(userId)){
            return false;
        }
        boardDao.updateBoard(board);//수정해주기
        return true;
    }

    @Override
    public boolean removeBoard(String userId, long boardId) {
        Board tmp = boardDao.selectByBoardId(boardId); //삭제 대상인 board
        System.out.println("userId: "+userId);
        System.out.println("tmp: "+tmp);

        if(tmp == null){
            return false;
        }
        if(!tmp.getUserId().equals(userId)){
            return false;
        }
        boardDao.deleteBoard(boardId); //삭제해주기
        return true;
    }
}

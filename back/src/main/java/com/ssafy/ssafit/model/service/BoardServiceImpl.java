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

    //검색
    @Override
    public List<Board> searchBoard(String keyword, String field) {
        return boardDao.searchByKeyword(keyword, field);
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
    public Long createBoard(String title, String tag, String content, MultipartFile image, String userId) {
        Board board = new Board();
        board.setUserId(userId);
        board.setTitle(title);
        board.setTag(tag);
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

        return board.getBoardId();
    }

    @Override
    public void updateBoard(Long boardId, String userId, String title, String content, String tag, MultipartFile image) {
        Board board = boardDao.selectByBoardId(boardId);
        if (!board.getUserId().equals(userId)) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        board.setTitle(title);
        board.setContent(content);
        board.setTag(tag);
        boardDao.updateBoard(board);

        if (image != null && !image.isEmpty()) {
            fileUploadUtil.save(image); // 이미지 저장
            imgDao.updateImage(boardId, image.getOriginalFilename());
        }
    }

    @Override
    public void removeBoard(String userId, long boardId) {
        Board board = boardDao.selectByBoardId(boardId); //삭제 대상인 board
        System.out.println("userId: "+userId);
        System.out.println("board: "+board);

        if (!board.getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한 없음");
        }

        boardDao.deleteBoard(boardId); //삭제해주기
    }
}

package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BoardDao;
import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private final BoardDao boardDao;

    @Override
    public List<Board> searchBoard(String keyword) {
        return boardDao.searchByKeyword(keyword);
    }

    @Override
    public List<Board> getAllBoards() {
        return boardDao.selectAll();
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
    public boolean createBoard(Board board) {
        //입력받은 값이 null 값이 아닌지 확인
        String userId = board.getUserId();
        String title = board.getTitle();
        String content = board.getContent();
        String tag = board.getTag();
        //입력 받은 board 중 null 값이 있으면 false
        if(userId==null || title==null || content==null || tag==null){
            return false;
        }
        return true;
//        System.out.println(board.getBoardId()); //null
//        boardDao.insertBoard(board);
//        System.out.println(board.getBoardId()); // 숫자

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

package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BoardDao;
import com.ssafy.ssafit.model.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public List<Board> searchBoard(String keyword) {
        return boardDao.searchByKeyword(keyword);
    }

    @Override
    public List<Board> getAllBoards() {
        return boardDao.selectAll();
    }

    @Override
    public Board getBoardByBoardId(int boardId) {
        return boardDao.selectByBoardId(boardId);
    }

    @Override
    public void createBoard(Board board) {
        boardDao.insertBoard(board);
    }

    @Override
    public void modifyBoard(int boardId) {
        boardDao.updateBoard(boardId);
    }

    @Override
    public boolean removeBoard(int boardId) {
        return boardDao.deleteBoard(boardId);
    }
}

package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BoardDao;
import com.ssafy.ssafit.model.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardDao boardDao;

    @Override
    public List<Board> select(Board board) {
        return boardDao.selectBoard();
    }

    @Override
    public Board selectDetail(String boardId) {
        return boardDao.selectDetailBoard(boardId);
    }

    @Override
    public void insert(Board board) {
        boardDao.insertBoard(board);
    }

    @Override
    public void update(String boardId) {
        boardDao.updateBoard(boardId);
    }

    @Override
    public boolean delete(String boardId) {
        return boardDao.deleteBoard(boardId);
    }
}

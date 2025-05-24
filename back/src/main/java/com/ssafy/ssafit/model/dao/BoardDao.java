package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {
    //board 검색 조회
    List<Board> searchByKeyword(@Param("keyword") String keyword);
    //board 전체 조회
    List<Board> selectAll();
    //board 상세 조회
    Board selectByBoardId(long boardId);
    //boardId로 title 조회
    String selectTitleByBoardId(long boardId);
    //boardId로 tag 조회
    String selectTagByBoardId(long boardId);
    
    //board 등록
    void insertBoard(Board board);
    //board 수정
    void updateBoard(Board board);
    //board 삭제
    void deleteBoard(long boardId);
}

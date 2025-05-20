package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Comment;

import java.util.List;

public interface CommentDao {
    //댓글 조회
    List<Comment> selectAllComment();
    //댓글 작성
    void insertComment(Comment comment);
    //댓글 수정
    void modifyComment(Comment comment);
    //댓글 삭제
    boolean deleteComment(int commentId);
}

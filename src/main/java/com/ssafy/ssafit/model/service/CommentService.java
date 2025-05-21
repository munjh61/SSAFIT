package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Comment;

import java.util.List;

public interface CommentService {
    //댓글 전체 조회
    List<Comment> getAllComment(long boardId);
    //댓글 작성
    void createComment(Comment comment, long boardId);
    //댓글 수정
    boolean modifyComment(String userId, Comment comment);
    //댓글 삭제
    boolean removeComment(String userId, long commentId);
}

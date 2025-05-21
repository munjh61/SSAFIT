package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.CommentDao;
import com.ssafy.ssafit.model.dto.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Override
    public List<Comment> getAllComment(long boardId) {
        return commentDao.selectAllComment(boardId);
    }

    @Override
    public void createComment(Comment comment, long boardId) {
        commentDao.insertComment(comment);
    }

    @Override
    public boolean modifyComment(String userId, Comment comment) {
        Comment tmp = commentDao.selectCommentByCommentId(comment.getCommentId());//수정 대상인 comment
        //수정 대상인 comment가 null 값이면, false
        if(tmp == null){
            return false;
        }
        //로그인 된 유저랑 댓글 작성한 유저가 같은지 확인
        if(!tmp.getUserId().equals(userId)){
            return false;
        }
        commentDao.updateComment(comment); //수정해주기
        return true;
    }

    @Override
    public boolean removeComment(String userId, long commentId) {
        Comment tmp = commentDao.selectCommentByCommentId(commentId);
        if(tmp == null){
            return false;
        }
        if(!tmp.getUserId().equals(userId)){
            return false;
        }
        commentDao.deleteComment(commentId);//삭제해주기
        return true;
    }
}

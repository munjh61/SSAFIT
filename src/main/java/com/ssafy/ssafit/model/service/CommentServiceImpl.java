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
    public List<Comment> getAllComment() {
        return commentDao.selectAllComment();
    }

    @Override
    public void createComment(Comment comment) {
        commentDao.insertComment(comment);
    }

    @Override
    public void modifyComment(Comment comment) {
        //여기
        commentDao.updateComment(comment);
    }

    @Override
    public boolean removeComment(long commentId) {

        commentDao.deleteComment(commentId);
        return true;
    }
}

package com.ssafy.ssafit.controller.comment;

import com.ssafy.ssafit.model.dto.Comment;
import com.ssafy.ssafit.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 수정
    @PutMapping("/{commentid}")
    public ResponseEntity<?> upadate(@PathVariable int commentId, @RequestBody Comment comment){
        comment.setCommentId(commentId);
        commentService.modifyC
    }

    //댓글 삭제
}

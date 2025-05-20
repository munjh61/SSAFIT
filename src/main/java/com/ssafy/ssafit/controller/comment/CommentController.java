package com.ssafy.ssafit.controller.comment;

import com.ssafy.ssafit.model.dto.Comment;
import com.ssafy.ssafit.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<?> upadate(@PathVariable long commentId, @RequestBody Comment comment){
        //댓글을 작성한 유저가 로그인된 유저랑 같은지 확인
        //Authorization
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoidXNlciIsInVzZXJOYW1lIjoi67CV7ZSE66Gg7Yq4IiwidXNlcklkIjoidXNlcjAzIiwiaWF0IjoxNzQ3NzIxMjYyLCJleHAiOjE3NDg5MzA4NjJ9.Au6-Ze8jG78wUgcEW0LEcdwno5t-Gzvw0GBC37MuomM
        //http://localhost:8080/api/auth/login
        //수정하려는 댓글이 존재하는지도 확인 / 삭제된것은 아닌지
        comment.setCommentId(commentId);
        commentService.modifyComment(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable long commentId){
        //삭제하려는 댓글을 작성한 유저가 로그인된 유저랑 같은지 확인
        boolean isDeleted = commentService.removeComment(commentId);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("comment delete");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
    }
}

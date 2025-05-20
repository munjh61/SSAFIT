package com.ssafy.ssafit.controller.comment;

import com.ssafy.ssafit.model.dto.Comment;
import com.ssafy.ssafit.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/public/comment")
@RequiredArgsConstructor
public class CommentPublicController {
    private final CommentService commentService;

    //댓글 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable long boardId){
        //board에 해당하는 댓글 조회
        List<Comment> list = commentService.getAllComment();
        return ResponseEntity.ok(list);
    }

    //댓글 작성
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Comment comment){
        //입력받은 comment가 null 값인지 확인
        commentService.createComment(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

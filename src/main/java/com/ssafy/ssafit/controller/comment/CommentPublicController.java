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
    @GetMapping("/board/{boardid}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable int commentId){
        List<Comment> list = commentService.getAllComment();
        return ResponseEntity.ok(list);
    }

    //댓글 작성
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Comment comment){
        commentService.createComment(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

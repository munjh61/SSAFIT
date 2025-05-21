package com.ssafy.ssafit.controller.board;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.service.BoardService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
//로그인 해야 가능한 기능
public class BoardController {
    private final BoardService boardService;

    //board 등록
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Board board){
        boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //board 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("boardId") long boardId, @RequestBody Board board){
//        log.info("요청 받은 board: {}", board);
//        log.info("로그인 사용자: {}", customUserDetails.getUsername());
//        board.setBoardId(boardId);
        board.setBoardId(boardId);
        String userId = customUserDetails.getUsername();
        boolean isUpdated = boardService.modifyBoard(userId, board);
        if(isUpdated){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패요");
    }

    //board 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long boardId){
        String userId = customUserDetails.getUsername();
        boolean isDeleted = boardService.removeBoard(userId, boardId);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("board delete");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패요");
    }
}

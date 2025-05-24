package com.ssafy.ssafit.controller.board;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.dto.Img;
import com.ssafy.ssafit.model.service.BoardService;
import com.ssafy.ssafit.model.service.ImgService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
//로그인 해야 가능한 기능
public class BoardController {
    private final BoardService boardService;
    private final ImgService imgService;

    //추천 board 조회
    @GetMapping("/recommend")
    public ResponseEntity<Map<String, Object>> recommend(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Map<String, Object> result = new HashMap<>();

        // 1. 게시글 전체 불러오기
        List<Board> boardList = boardService.getAllBoards();

        // 2. 게시글 ID별 이미지 매핑
        Map<Long, List<Img>> boardImgs = new HashMap<>();
        for (Board board : boardList) {
            Long boardId = board.getBoardId();
            List<Img> imgs = imgService.getImgByBoardId(boardId);
            boardImgs.put(boardId, imgs);
        }

        result.put("boards", boardList);
        result.put("images", boardImgs);

        return ResponseEntity.ok(result);
    }

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

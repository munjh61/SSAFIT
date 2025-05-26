package com.ssafy.ssafit.controller.board;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.dto.Img;
import com.ssafy.ssafit.model.service.BoardService;
import com.ssafy.ssafit.model.service.FollowService;
import com.ssafy.ssafit.model.service.ImgService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final FollowService followService;

    //로그인 유저가 작성한 보드만 조회 - 마이페이지용
    //페이지 주인의 보드 가져오기
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getBoardByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String loginUser = customUserDetails.getUsername();
        Map<String, Object> result = new HashMap<>();

        //1. 로그인 된 유저가 쓴 보드 불러오기
        List<Board> boardList = boardService.getBoardByUserId(loginUser);
        result.put("boards", boardList);
        //2. 불러와진 보드 별 이미지 묶기
        Map<Long, List<Img>> boardImgs = new HashMap<>();
        for (Board board : boardList) {
            List<Img> imgList = imgService.getImgByBoardId(board.getBoardId());
            boardImgs.put(board.getBoardId(), imgList);
        }
        result.put("images", boardImgs);

        return ResponseEntity.ok(result);
    }

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
    public ResponseEntity<Map<String, Object>> create(@RequestPart("title") String title,
                                    @RequestPart("tag") String tag, @RequestPart("content") String content,
                                    @RequestPart(value = "image", required = false) MultipartFile image,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        String userId = userDetails.getUsername();
        Long boardId = boardService.createBoard(title, tag, content, image, userId);

        Map<String, Object> result = new HashMap<>();
        result.put("boardId", boardId);

        return ResponseEntity.ok(result);
    }

    //board 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> update(@PathVariable Long boardId,
                                    @RequestPart("title") String title,
                                    @RequestPart("tag") String tag,
                                    @RequestPart("content") String content,
                                    @RequestPart(value = "image", required = false) MultipartFile image,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
//        log.info("요청 받은 board: {}", board);
//        log.info("로그인 사용자: {}", customUserDetails.getUsername());
//        board.setBoardId(boardId);
        String userId = userDetails.getUsername();
        boardService.updateBoard(boardId, userId, title, content, tag, image);

        return ResponseEntity.ok().build();
    }

    //board 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long boardId) {
        String userId = customUserDetails.getUsername();
        boardService.removeBoard(userId, boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //board 추천
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendedExercises(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String userId = userDetails.getUsername();
        List<Board> recommendedBoard = new ArrayList<>();

        try {
            // 1. 로그인한 사용자가 팔로잉하고 있는 유저 목록 가져오기
            List<String> followingUserIds = followService.getFollowingUserIds(userId);

            // 2. 해당 유저들이 작성한 게시글 목록 가져오기
            for (String followeeId : followingUserIds) {
                List<Board> boards = boardService.getBoardByUserId(followeeId);
                recommendedBoard.addAll(boards);
            }

            return ResponseEntity.ok(recommendedBoard);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("추천 운동 조회 중 오류가 발생했습니다.");
        }
    }

}

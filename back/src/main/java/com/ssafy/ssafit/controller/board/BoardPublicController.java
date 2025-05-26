package com.ssafy.ssafit.controller.board;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.dto.Img;
import com.ssafy.ssafit.model.service.BoardService;
import com.ssafy.ssafit.model.service.ImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/public/board")
@RequiredArgsConstructor
//로그인 안해도 할 수 있는 기능
public class BoardPublicController {
    private final BoardService boardService;
    private final ImgService imgService;

    //board 검색 조회
    //검색어에 걸리는 모든 게시글 조회
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBoard(@RequestParam String keyword,
                                         @RequestParam String field){
        //게시글 검색
        List<Board> result = boardService.searchBoard(keyword, field);

        //게시글 ID 리스트 뽑기
        List<Long> boardIds = result.stream()
                .map(Board::getBoardId)
                .collect(Collectors.toList());

        //게시글 별 이미지 리스트 조회
        Map<Long, List<Img>> boardImages = imgService.getImgListByBoardId(boardIds);

        //결과 묶어서 반환
        Map<String, Object> map = new HashMap<>();
        map.put("boards", result);
        map.put("images", boardImages);

        System.out.println("📥 field: " + field);

        return ResponseEntity.ok(map);
    }

    //board 전체 조회
    @GetMapping("")
    public ResponseEntity<List<Board>> getAllBoard(){
        List<Board> list = boardService.getAllBoards();
        return ResponseEntity.ok(list);
    }

    //board 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getBoard(@PathVariable long boardId){
        Board board = boardService.getBoardByBoardId(boardId);
        List<Img> imgList = imgService.getImgByBoardId(boardId);

        Map<Long, List<Img>> imagesMap = new HashMap<>();
        imagesMap.put(boardId, imgList);

        Map<String, Object> result = new HashMap<>();
        result.put("board", board);
        result.put("images", imagesMap);

        return ResponseEntity.ok(result);
    }


}

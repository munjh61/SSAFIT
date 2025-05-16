package com.ssafy.ssafit.controller.board;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/public/board")
@RequiredArgsConstructor
//로그인 안해도 할 수 있는 기능
public class BoardPublicController {

    private final BoardService boardService;

    //board 검색 조회
    //board 하나 고르기 -> 검색, 특정 보드 조회
    //검색어에 걸리는 모든 게시글 조회
    @GetMapping("/{boardid}")

    //board 전체 조회


    //board 상세 조회
    @GetMapping("/")



}

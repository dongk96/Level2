package com.sparta.week_hw2.controller;

import com.sparta.week_hw2.dto.BoardRequestDto;
import com.sparta.week_hw2.dto.BoardResponseDto;
import com.sparta.week_hw2.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/boards/create")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        return boardService.createBoard(boardRequestDto, httpServletRequest);
    }

    @GetMapping("/boards/list")
    public List<BoardResponseDto> getBoardList(){
        return boardService.getBoardList();
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/boards/{id}")
    public Long updateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest httpServletRequest){
        return boardService.updateBoard(id, boardRequestDto, httpServletRequest);
    }

    @DeleteMapping("/boards/{id}")
    public String deleteBoard(
            @PathVariable Long id,
            @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest httpServletRequest){
        return boardService.deleteBoard(id, boardRequestDto, httpServletRequest);
    }
}

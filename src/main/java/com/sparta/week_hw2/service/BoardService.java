package com.sparta.week_hw2.service;

import com.sparta.week_hw2.dto.BoardRequestDto;
import com.sparta.week_hw2.dto.BoardResponseDto;
import com.sparta.week_hw2.entity.Board;
import com.sparta.week_hw2.entity.User;
import com.sparta.week_hw2.jwt.JwtUtil;
import com.sparta.week_hw2.repository.BoardRepository;
import com.sparta.week_hw2.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.saveAndFlush(new Board(boardRequestDto,  user.getUsername(), user.getId()));

            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 게시글이 없습니다.")
        );

        return new BoardResponseDto(board);
    }

    @Transactional
    public Long updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findByIdAndUserid(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
            );

            board.update(boardRequestDto);

            return board.getId();

        } else {
            return null;
        }
    }
    @Transactional
    public String deleteBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 삭제 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findByIdAndUserid(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
            );
            boardRepository.delete(board);

            return "삭제 성공";
        }else {
            return null;
        }
    }
}

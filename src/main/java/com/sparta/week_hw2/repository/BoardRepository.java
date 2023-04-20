package com.sparta.week_hw2.repository;

import com.sparta.week_hw2.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndUserid(Long id, Long userId);
}

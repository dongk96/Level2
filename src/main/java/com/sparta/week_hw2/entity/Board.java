package com.sparta.week_hw2.entity;

import com.sparta.week_hw2.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long userid;

    // Board 클래스의 생성자
    // -> CourseRequestDto 객체를 인자로 받아 해당 객체의 필드 값을 가져와 title, instructor, cost 필드에 할당
    public Board(BoardRequestDto boardRequestDto, String author, Long user_id) {
        this.title = boardRequestDto.getTitle();
        this.author = author;
        this.userid = user_id;
        this.contents = boardRequestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
    }
}

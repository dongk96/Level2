package com.sparta.week_hw2.controller;

import com.sparta.week_hw2.dto.LoginRequestDto;
import com.sparta.week_hw2.dto.LoginResponseDto;
import com.sparta.week_hw2.dto.SignupRequestDto;
import com.sparta.week_hw2.dto.SignupResponseDto;
import com.sparta.week_hw2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
// template에 있는 html 파일만 받아오기 때문에 @Controller 사용
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/user/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}

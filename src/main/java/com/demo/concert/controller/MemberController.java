package com.demo.concert.controller;

import com.demo.concert.component.login.JwtProvider;
import com.demo.concert.dto.LoginRequest;
import com.demo.concert.dto.api.ApiResponse;
import com.demo.concert.entity.login.Member;
import com.demo.concert.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
  private final MemberServiceImpl memberService;
  private final JwtProvider jwtProvider;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
    Member member = memberService.authenticate(request.getUsername(), request.getPassword());
    String token = jwtProvider.createToken(member.getUsername(), member.getUserRole());

    return ResponseEntity.ok(new ApiResponse<>(200, "로그인 성공", token));
  }
}

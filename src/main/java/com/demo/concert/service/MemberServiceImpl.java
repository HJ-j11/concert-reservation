package com.demo.concert.service;

import com.demo.concert.entity.login.Member;
import com.demo.concert.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 로그인 시 인증
   * @param username 사용자가 입력한 아이디
   * @param password 사용자가 입력한 비밀번호
   * @return 인증 성공 시 Member 객체
   * @throws RuntimeException 인증 실패 시 예외
   */
  @Override
  public Member authenticate(String username, String password) {
    Member member = memberRepository.findMemberByUsername(username)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

    // 비밀번호 검증
    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    return member;
  }
}

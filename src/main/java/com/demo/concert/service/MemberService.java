package com.demo.concert.service;

import com.demo.concert.entity.login.Member;

public interface MemberService {
  Member authenticate(String username, String rawPassword);
}

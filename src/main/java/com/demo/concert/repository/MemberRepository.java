package com.demo.concert.repository;

import com.demo.concert.entity.login.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
  Optional<Member> findMemberByUsername(String username);
}

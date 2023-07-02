package com.zerozae.blog.repository.member;

import com.zerozae.blog.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}

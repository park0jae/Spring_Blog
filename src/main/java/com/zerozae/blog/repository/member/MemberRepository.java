package com.zerozae.blog.repository.member;

import com.zerozae.blog.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findOneWithRolesByUsername(String username);
    Optional<Member> findByUsername(String username);

    boolean existsMemberByUsername(String username);
    boolean existsMemberByNickname(String username);

}

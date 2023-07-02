package com.zerozae.blog.domain.member;


import com.zerozae.blog.domain.role.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 비식별관계로 설정
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}

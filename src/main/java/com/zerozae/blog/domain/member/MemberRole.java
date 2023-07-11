package com.zerozae.blog.domain.member;


import com.zerozae.blog.domain.role.Role;
import lombok.*;

import javax.persistence.*;

/**
 * 비식별관계로 설정
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@IdClass(MemberRoleId.class)
public class MemberRole {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public MemberRole(Role role) {
        this.role = role;
    }
}

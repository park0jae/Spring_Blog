package com.zerozae.blog.domain.member;

import com.zerozae.blog.domain.role.Role;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberRoleId implements Serializable {
    private Member member;
    private Role role;
}

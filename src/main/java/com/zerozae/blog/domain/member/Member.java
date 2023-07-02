package com.zerozae.blog.domain.member;


import com.zerozae.blog.domain.base.BaseEntity;
import com.zerozae.blog.domain.board.Board;
import com.zerozae.blog.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // => 비밀번호 암호화 할 예정이므로 넉넉하게 잡아줌
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "member" )
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();
}

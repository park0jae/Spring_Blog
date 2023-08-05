package com.zerozae.blog.domain.member;


import com.zerozae.blog.domain.base.BaseEntity;
import com.zerozae.blog.domain.board.Board;
import com.zerozae.blog.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    private String nickname;

    String provider;

    @OneToMany(mappedBy = "member" )
    private List<MemberRole> roles = new ArrayList<>();

    public Member(String username, String password,  String email, String nickname,  String provider,  List<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
        addRoles(roles);
    }

    private void addRoles(List<Role> roles) {
        List<MemberRole> roleList = roles.stream().map(role -> new MemberRole(this, role)).collect(Collectors.toList());
        this.roles = roleList;
    }

    public void update(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


}

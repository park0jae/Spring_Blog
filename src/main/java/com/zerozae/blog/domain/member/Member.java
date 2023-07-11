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
    private String username; // 아이디

    @Column(nullable = false, length = 100) // => 비밀번호 암호화 할 예정이므로 넉넉하게 잡아줌
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "member" )
    private List<MemberRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    String provider;

    public void updateMember(Board board){
        boards.add(board);
    }

    public Member(String username, String password,   String provider,  List<Role> roles, List<Board> boards) {
        this.username = username;
        this.password = password;
        this.provider = provider;
        initPosts(boards);
        addRoles(roles);
    }

    private void addRoles(List<Role> roles) {
        List<MemberRole> roleList = roles.stream().map(role -> new MemberRole(this, role)).collect(Collectors.toList());
        this.roles = roleList;
    }


    private void initPosts(List<Board> posts) {
        if (!posts.isEmpty()) {
            posts.stream().forEach(
                    p -> {
                        posts.add(p);
                        p.initMember(this);
                    }
            );
        }
    }

    public void update(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


}

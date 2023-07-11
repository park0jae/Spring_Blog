package com.zerozae.blog.domain.board;

import com.zerozae.blog.domain.base.BaseEntity;
import com.zerozae.blog.domain.member.Member;
import com.zerozae.blog.domain.reply.Reply;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @ColumnDefault("0")
    private int count;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Reply> replies;

    public Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
        member.updateMember(this);
    }

    public void initMember(Member member) {
        if (this.member == null) {
            this.member = member;
        }
    }


    // update Board
    public void update(String title, String content){
        this.title=title;
        this.content=content;
        this.member.updateMember(this);
    }
}

package com.zerozae.blog.domain.board;

import com.zerozae.blog.domain.base.BaseEntity;
import com.zerozae.blog.domain.image.BoardImage;
import com.zerozae.blog.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zerozae.blog.helper.BoardImageHelper.addImages;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImage> boardImages;

    public Board(String title, String content, Member member, List<BoardImage> boardImages){
        this.title = title;
        this.content = content;
        this.member = member;
        this.boardImages = new ArrayList<>();

        addImages(boardImages, this.boardImages, this);
    }

//    public BoardUpdateResponseDto updateBoard(BoardUpdateRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//
//        Map<String, List<BoardImage>> m = updateImage(requestDto, this.boardImages, this);
//        return BoardUpdateResponseDto.toDto(requestDto, this, m);
//    }

    // update Board
    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }
}

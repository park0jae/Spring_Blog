package com.zerozae.blog.domain.image;

import com.zerozae.blog.domain.board.Board;
import com.zerozae.blog.exception.image.NoExtException;
import com.zerozae.blog.exception.image.UnSupportExtException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage {

    private static final String extension[] = {"jpg", "jpeg", "gif", "bmp", "png"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_image_id")
    private Long id;

    @Column(nullable = false)
    private String uniqueName;

    @Column(nullable = false)
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    public BoardImage(String originName) {
        this.originName = originName;
        this.uniqueName = extractExtAndGenerateUniqueName(originName);
    }

    private String extractExtAndGenerateUniqueName(String originName) {
        String ext = getExt(originName);
        return UUID.randomUUID().toString() + "." + ext;
    }

    private String getExt(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if (supportFormat(ext)) {
                return ext;
            } else {
                throw new UnSupportExtException();
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new NoExtException();
        } catch (UnSupportExtException e) {
            throw e;
        }
    }

    private boolean supportFormat(String ext) {
        return Arrays.stream(extension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }

    public void initBoard(Board board) {
        if (this.board == null) {
            this.board = board;
        }
    }

    public void cancel(Board post) {
        if (this.board != null) {
            this.board = null;
        }
    }

}

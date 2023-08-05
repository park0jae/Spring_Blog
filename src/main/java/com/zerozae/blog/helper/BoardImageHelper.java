package com.zerozae.blog.helper;

import com.zerozae.blog.domain.board.Board;
import com.zerozae.blog.domain.image.BoardImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoardImageHelper {

    public static List<BoardImage> addToDBAndServer(List<MultipartFile> addImages, List<BoardImage> boardImages, Board board) {
        List<BoardImage> addPostImageList = MultipartToImage(addImages);
        addImages(addPostImageList, boardImages, board);

        return addPostImageList;
    }
    public static List<BoardImage> deleteFromDBAndServer(List<String> deleteImageNames, List<BoardImage> boardImages, Board board) {
        List<BoardImage> deletePostImageList = StringToImage(deleteImageNames, boardImages);
        deleteImages(deletePostImageList, boardImages, board);

        return deletePostImageList;
    }

//    public static Map<String, List<BoardImage>> updateImage(BoardUpdateRequestDto requestDto, List<BoardImage> boardImages, Board board) {
//
//        Map<String, List<BoardImage>> m = new HashMap<>();
//
//        // 수정/DB - 추가된 이미지 데이터베이스에 올리고, 삭제된 이미지 데이터베이스에서 삭제
//        if (requestDto.getAddImages() != null) {
//            List<MultipartFile> addImages = requestDto.getAddImages();  // 업로드할 이미지 파일
//            List<BoardImage> addList = addToDBAndServer(addImages, boardImages, board);
//            m.put("addList", addList);
//        }
//
//        if (requestDto.getDeleteImageNames() != null) {
//            List<String> deleteImageNames = requestDto.getDeleteImageNames(); // 삭제할 이미지 파일 이름
//            List<BoardImage> deleteList = deleteFromDBAndServer(deleteImageNames, boardImages, board);
//            m.put("deleteList", deleteList);
//        }
//        return m;
//    }

    public static void addImages(List<BoardImage> addList, List<BoardImage> postImages, Board board) {
        addList.stream().forEach(
                i -> {
                    postImages.add(i);
                    i.initBoard(board);
                });
    }
    private static void deleteImages(List<BoardImage> deleteList, List<BoardImage> postImages, Board board) {
        deleteList.stream().forEach(
                i -> {
                    postImages.remove(i);
                    i.cancel(board);
                }
        );
    }

    private static List<BoardImage> StringToImage(List<String> deleteImageNames, List<BoardImage> boardImages) {
        return deleteImageNames.stream().map(name -> convertNameToImage(name, boardImages))
                .filter(i -> i.isPresent())
                .map(i -> i.get())
                .collect(Collectors.toList());
    }

    private static List<BoardImage> MultipartToImage(List<MultipartFile> addImages) {
        return addImages.stream().map(file -> new BoardImage(file.getOriginalFilename())).collect(Collectors.toList());
    }

    private static Optional<BoardImage> convertNameToImage(String name, List<BoardImage> boardImages) {
        return boardImages.stream().filter(i -> i.getOriginName().equals(name)).findAny();
    }
}

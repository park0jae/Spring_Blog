package com.zerozae.blog.repository.board;

import com.zerozae.blog.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

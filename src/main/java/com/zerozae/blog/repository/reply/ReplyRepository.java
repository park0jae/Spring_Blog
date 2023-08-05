package com.zerozae.blog.repository.reply;

import com.zerozae.blog.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}

package com.projects.redditclone.repository;

import com.projects.redditclone.model.Comment;
import com.projects.redditclone.model.Post;
import com.projects.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}

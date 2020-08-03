package com.projects.redditclone.repository;

import com.projects.redditclone.model.Post;
import com.projects.redditclone.model.Subreddit;
import com.projects.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findAllBySubreddit(Subreddit subreddit);
}

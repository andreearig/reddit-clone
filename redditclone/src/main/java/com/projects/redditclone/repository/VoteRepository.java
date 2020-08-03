package com.projects.redditclone.repository;

import com.projects.redditclone.model.Post;
import com.projects.redditclone.model.User;
import com.projects.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}

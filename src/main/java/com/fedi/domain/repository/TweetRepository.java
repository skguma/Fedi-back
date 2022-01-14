package com.fedi.domain.repository;

import com.fedi.domain.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);
    List<Tweet> findAllById(Iterable<Long> ids);
}

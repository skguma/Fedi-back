package com.fedi.domain.repository;

import com.fedi.domain.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Optional<Tweet> findById(Long id);
    List<Tweet> findAllById(Iterable<Long> ids);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Tweet t SET t.retweets = :retweets WHERE t.tweetId = :id")
    int updateRetweets(String retweets, Long id);

}

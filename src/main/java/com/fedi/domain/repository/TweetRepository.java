package com.fedi.domain.repository;

<<<<<<< Updated upstream
import com.fedi.domain.entity.Tweet;
=======
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
>>>>>>> Stashed changes
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
<<<<<<< Updated upstream
    Optional<Tweet> findById(Long id);
    List<Tweet> findAllById(Iterable<Long> ids);
    Tweet findByTweetUrlAndDeleteFlagFalse(String tweetUrl);
=======

	
	public Optional<Tweet> findById(Long id);

//	@EntityGraph(attributePaths = {"account"})
//	public List<Tweet> findAllById(Iterable<Long> ids);
	
	public List<Tweet> findAllById(Iterable<Long> ids);
>>>>>>> Stashed changes

}

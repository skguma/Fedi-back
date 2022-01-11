package com.fedi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fedi.domain.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

	
	public Optional<Tweet> findById(Long id);

	
	public List<Tweet> findAllById(Iterable<Long> ids);

}

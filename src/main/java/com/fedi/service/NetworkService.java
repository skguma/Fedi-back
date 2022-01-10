package com.fedi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fedi.dto.NetworkResponseDto;
import com.fedi.entity.Tweet;
import com.fedi.repository.TweetRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NetworkService {
	
	@Autowired
	private TweetRepository tweetRepository;
	
	public Optional<Tweet> findById(Long tweetId){
		Optional<Tweet> tweet = tweetRepository.findById(tweetId);
		return tweet;
	}
	
	public List<Tweet> findAllById(Iterable<Long> ids){
		List<Tweet> tweets = tweetRepository.findAllById(ids);
		return tweets;
	}
	
	@Transactional(readOnly = true)
	public List<NetworkResponseDto> searchAllById(Iterable<Long> ids){
		return tweetRepository.findAllById(ids).stream()
				.map(NetworkResponseDto::new)
				.collect(Collectors.toList());
	}
}

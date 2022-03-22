package com.fedi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fedi.domain.entity.Tweet;
import com.fedi.domain.repository.TweetRepository;
import com.fedi.web.dto.LikeRpaRequestDto;
import com.fedi.web.dto.NetworkResponseDto;
import com.fedi.web.dto.NodeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NetworkService {
	
	private final TweetRepository tweetRepository;
	
	@Transactional(readOnly = true)
	public List<Tweet> findTweetsbyId(Iterable<Long> ids){
		List<Tweet> tweets = (List<Tweet>)tweetRepository.findAllById(ids);
		
		return tweets;
	}
	
	public List<LikeRpaRequestDto> extractUrl(List<Tweet> tweets){
		List<LikeRpaRequestDto> requestDtos = tweets.stream().map(LikeRpaRequestDto::new).collect(Collectors.toList());
		
//		tweetUrl에서 "/photo/1" 제거
		for (LikeRpaRequestDto dto : requestDtos) {
			String url = dto.getTweetUrl();
			url = url.substring(0, url.lastIndexOf("/", url.lastIndexOf("/")-1));
			dto.setTweetUrl(url);
		}
		return requestDtos;		
	}
	
}

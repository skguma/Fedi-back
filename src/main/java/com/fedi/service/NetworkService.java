package com.fedi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fedi.domain.entity.Tweet;
import com.fedi.domain.repository.ImageRepository;
import com.fedi.domain.repository.TweetRepository;
import com.fedi.web.dto.RpaRequestDto;
import com.fedi.web.dto.LikeRpaResponseDto;
import com.fedi.web.dto.NetworkResponseDto;
import com.fedi.web.dto.NodeDto;
import com.google.gson.Gson;

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
	
	public List<String> extractUrl(List<Tweet> tweets){
		List<String> extractUrls = new ArrayList<>();
//		tweetUrl에서 "/photo/1" 제거
		for (Tweet t : tweets) {
			String url = t.getTweetUrl();
			url = url.substring(0, url.lastIndexOf("/", url.lastIndexOf("/")-1));
			extractUrls.add(url);
		}
		return extractUrls;		
	}
	
	public List<RpaRequestDto> updateRetweets(List<LikeRpaResponseDto> response, List<Tweet> tweets) {
		List<LikeRpaResponseDto> retweets = new ArrayList<>(); // db에 추가할 내용.
		List<RpaRequestDto> requests = new ArrayList<>(); // 신고 rpa에 넘겨줄 url
		int idx = 0; 

		for (LikeRpaResponseDto e : response) {
			RpaRequestDto request = new RpaRequestDto(e.getUrl());
			requests.add(request);
			if (e.getSourceId().equals(tweets.get(idx).getAccount().getAccountId())){
				retweets.add(e);
			}
			else {
				Map<String, List<LikeRpaResponseDto>> retweetMap = new HashMap();
				retweetMap.put("retweets", retweets);
				String retweet = new Gson().toJson(retweetMap);
				tweetRepository.updateRetweets(retweet, tweets.get(idx).getTweetId());
				idx++;
				retweets.clear();
			}
		}
		Map<String, List<LikeRpaResponseDto>> retweetMap = new HashMap();
		retweetMap.put("retweets", retweets);
		String retweet = new Gson().toJson(retweetMap);
		tweetRepository.updateRetweets(retweet, tweets.get(idx).getTweetId());
		idx++;
		return requests;
		
		
	}
}

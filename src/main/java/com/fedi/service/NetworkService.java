package com.fedi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fedi.domain.repository.TweetRepository;
import com.fedi.web.dto.LikeRpaRequestDto;
import com.fedi.web.dto.NetworkResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NetworkService {
	
	private final TweetRepository tweetRepository;
	
//	@Transactional(readOnly = true)
//	public List<NetworkResponseDto> searchAllById(Iterable<Long> ids){
//		return tweetRepository.findAllById(ids).stream()
//				.map(NetworkResponseDto::new)
//				.collect(Collectors.toList());
//	}
	
	@Transactional(readOnly = true)
	public List<LikeRpaRequestDto> findAllbyId(Iterable<Long> ids){
		List<LikeRpaRequestDto> requestDtos = tweetRepository.findAllById(ids).stream()
												.map(LikeRpaRequestDto::new)
												.collect(Collectors.toList());
//		tweetUrl에서 "/photo/1" 제거
		for (LikeRpaRequestDto dto : requestDtos) {
			String url = dto.getTweetUrl();
			url = url.substring(0, url.lastIndexOf("/", url.lastIndexOf("/")-1));
			dto.setTweetUrl(url);
		}
		return requestDtos;
	}
}

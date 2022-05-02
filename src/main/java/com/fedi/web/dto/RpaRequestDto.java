package com.fedi.web.dto;

import com.fedi.domain.entity.Tweet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RpaRequestDto {
	private String tweetUrl;
	
	public RpaRequestDto(Tweet entity) {
		this.tweetUrl = entity.getTweetUrl();
	}
	
	public RpaRequestDto(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}
}

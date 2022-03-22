package com.fedi.web.dto;

import com.fedi.domain.entity.Tweet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRpaRequestDto {
	private String tweetUrl;
	
	public LikeRpaRequestDto(Tweet entity) {
		this.tweetUrl = entity.getTweetUrl();
	}
}

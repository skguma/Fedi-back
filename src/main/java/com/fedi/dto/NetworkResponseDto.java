package com.fedi.dto;

import com.fedi.entity.Tweet;

import lombok.Getter;

@Getter
public class NetworkResponseDto {
	private Long tweetId;
	private String accountId;
	private String likes;
	private String retweets;
	
	public NetworkResponseDto(Tweet entity) {
		this.tweetId = entity.getTweetId();
		this.accountId = entity.getAccountId();
		this.likes = entity.getLikes();
		this.retweets = entity.getRetweets();
	}
}

package com.fedi.web.dto;

import java.util.ArrayList;

import com.fedi.domain.entity.Tweet;

import lombok.Getter;

@Getter
public class NetworkResponseDto {
	private Long tweetId;
	private String accountId;
	private String accountName;
	private String likes;
	private String retweets;
	
	public NetworkResponseDto(Tweet entity) {
		this.tweetId = entity.getTweetId();
		this.accountId = entity.getAccount().getAccountId();
		this.accountName = entity.getAccount().getAccountName();
		this.likes = entity.getLikes();
		this.retweets = entity.getRetweets();
	}
	
	private ArrayList<NodeDto> nodes;
	private ArrayList<LinkDto> links;
	
	public NetworkResponseDto(ArrayList<NodeDto> nodes, ArrayList<LinkDto> links) {
		this.nodes = nodes;
		this.links = links;
	}
}

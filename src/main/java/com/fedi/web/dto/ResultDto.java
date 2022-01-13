package com.fedi.web.dto;

import com.fedi.domain.entity.Analysis;

import lombok.Getter;

@Getter
public class ResultDto {
	private String imageUrl;
	private String eyes;
	private Double similarity;
	private String tweetUrl;
	private Long tweetId; 
	
	public ResultDto(Analysis analysis) {
		this.imageUrl = analysis.getImage().getImageUrl();
		this.eyes = analysis.getImage().getEyes();
		this.similarity = analysis.getSimilarity();
		this.tweetUrl = analysis.getImage().getTweet().getTweetUrl();
		this.tweetId = analysis.getImage().getTweet().getTweetId();
	}
}

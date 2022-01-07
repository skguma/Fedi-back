package com.fedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tweet")
public class Tweet {
	@Id
	@Column(name = "tweet_id")
	private Long tweetId;
	
	@Column(name = "account_id")
	private String accountId;
	
	@Column(name = "tweet_url")
	private String tweetUrl;
	
	private String likes;
	
	private String retweets;
	
}

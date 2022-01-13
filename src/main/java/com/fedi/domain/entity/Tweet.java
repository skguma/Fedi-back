package com.fedi.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Tweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tweet_id")
	private Long tweetId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
	
	@Column(name = "tweet_url")
	private String tweetUrl;
	
	private String likes;
	
	private String retweets;
	
	private int deleteFlag;
	
	@OneToMany(mappedBy = "tweet")
	private List<Image> images = new ArrayList<>();
	
	@Builder
	public Tweet(Account account, String tweetUrl, String likes, String retweets, int deleteFlag) {
		this.account = account;
		this.tweetUrl = tweetUrl;
		this.likes = likes;
		this.retweets = retweets;
		this.deleteFlag = deleteFlag;
	}
	
}

package com.fedi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Tweet {
<<<<<<< Updated upstream

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_id")
    private Long tweetId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "tweet_url")
    private String tweetUrl;

    private String likes;

    private String retweets;

    private Boolean deleteFlag;

    @Builder
    public Tweet(String accountId, String tweetUrl, String likes, String retweets, Boolean deleteFlag) {
        this.accountId = accountId;
        this.tweetUrl = tweetUrl;
        this.likes = likes;
        this.retweets = retweets;
        this.deleteFlag = deleteFlag;
    }

=======
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tweet_id")
	private Long tweetId;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "account_id")
//	private Account account;
	
	@Column
	private String accountId;
	
	@Column(name = "tweet_url")
	private String tweetUrl;
	
	private String likes;
	
	private String retweets;
	
	private int deleteFlag;
	
//	@OneToMany(mappedBy = "tweet")
//	private List<Image> images = new ArrayList<>();
	
	@Builder
	public Tweet(String accountId, String tweetUrl, String likes, String retweets, int deleteFlag) {
		this.accountId = accountId;
		this.tweetUrl = tweetUrl;
		this.likes = likes;
		this.retweets = retweets;
		this.deleteFlag = deleteFlag;
	}
	
>>>>>>> Stashed changes
}

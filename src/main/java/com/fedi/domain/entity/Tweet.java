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

    private Boolean reportFlag;

    @Builder
    public Tweet(String accountId, String tweetUrl, String likes, String retweets, Boolean reportFlag) {
        this.accountId = accountId;
        this.tweetUrl = tweetUrl;
        this.likes = likes;
        this.retweets = retweets;
        this.reportFlag = reportFlag;
    }

    public void updateReportFlag(){
        this.reportFlag = true;
    }

}

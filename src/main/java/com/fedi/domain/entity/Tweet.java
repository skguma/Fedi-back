package com.fedi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    private Boolean reportFlag;
    
    private Boolean suspendFlag;
    
    private String retweets;

    @Builder
    public Tweet(Account account, String tweetUrl, Boolean reportFlag, Boolean suspendFlag) {
        this.account = account;
        this.tweetUrl = tweetUrl;
        this.reportFlag = reportFlag;
        this.suspendFlag = suspendFlag;
    }

    public void updateReportFlag(){
        this.reportFlag = true;
    }
    
    public void updateSuspendFlag() {
    	this.suspendFlag = true;
    }
}

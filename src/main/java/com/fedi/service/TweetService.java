package com.fedi.service;

import com.fedi.domain.entity.Tweet;
import com.fedi.domain.repository.ImageRepository;
import com.fedi.domain.repository.TweetRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final ImageRepository imageRepository;

    public String reportTweet(Long tweetId) throws IllegalArgumentException{
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new IllegalArgumentException("no tweet id: "+tweetId));

        if(tweet.getReportFlag()){
            return "already reported";
        }else{
            tweet.updateReportFlag();
            tweetRepository.save(tweet);
            return "success";
        }
    }
    
    public String reportTweets(List<Long> tweetIds) {
    	List<Tweet> tweets = tweetRepository.findAllById(tweetIds);
    	for (Tweet tweet : tweets) {
    		if (tweet.getReportFlag()) {
    			continue;
    		} else {
    			tweet.updateReportFlag();
    			tweetRepository.save(tweet);
    		}
    	}
    	return "success";
    }
    
    public String suspendTweet(Long tweetId) {
    	Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new IllegalArgumentException("no tweet id: "+tweetId));

        if(tweet.getSuspendFlag()){
            return "already suspended";
        }else{
            tweet.updateSuspendFlag();
            tweetRepository.save(tweet);
            return "success";
        }
    }
    
    @Transactional(readOnly = true)
    public List<Tweet> findTweets(List<Long> ids) {
		List<Tweet> tweets = imageRepository.findTweetUrlByImageId(ids);
		return tweets;
	}
    
    @Transactional(readOnly = true)
    public Tweet findTweet(Long id) {
    	return imageRepository.findTweetByImageId(id);
    }
}

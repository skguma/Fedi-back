package com.fedi.service;

import com.fedi.domain.entity.Tweet;
import com.fedi.domain.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TweetService {
    private final TweetRepository tweetRepository;

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
}

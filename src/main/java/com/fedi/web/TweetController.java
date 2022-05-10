package com.fedi.web;

import com.fedi.domain.entity.Tweet;
import com.fedi.service.ImageService;
import com.fedi.service.TweetService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TweetController {
    private final TweetService tweetService;
    private final ImageService imageService;

    @PatchMapping("/tweets/{tweetId}/report")
    public String reportTweet(@PathVariable Long tweetId){
        return tweetService.reportTweet(tweetId);
    }
    
    @PatchMapping("/tweets/{imageId}/suspend")
    public String suspendTweet(@PathVariable Long imageId) throws IOException {
    	Long tweetId = tweetService.findTweet(imageId).getTweetId();
    	return tweetService.suspendTweet(tweetId);
    	
    }
    
    @GetMapping("/tweets/{imageId}/accountInfo")
    public Map<String, Object> getAccountInfo(@PathVariable Long[] imageId) {
    	HashMap<String, Object> accountInfo = new HashMap<String, Object>();
    	List<Long> imageIds = Arrays.asList(imageId);
    	List<Tweet> tweets = tweetService.findTweets(imageIds);
    	List<String> accountNames = new ArrayList<>();
    	
    	for (Tweet tweet : tweets) {
    		accountNames.add(tweet.getAccount().getAccountName());
    	}
    	
    	accountInfo.put("name", accountNames);
    	
    	return accountInfo;
    }

}

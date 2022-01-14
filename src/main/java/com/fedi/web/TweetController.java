package com.fedi.web;

import com.fedi.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TweetController {
    private final TweetService tweetService;

    @PatchMapping("/tweets/{tweetId}/report")
    public String reportTweet(@PathVariable Long tweetId){
        return tweetService.reportTweet(tweetId);
    }

}

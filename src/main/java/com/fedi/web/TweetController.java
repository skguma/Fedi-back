package com.fedi.web;

import com.fedi.service.AnalysisService;
import com.fedi.service.ResultService;
import com.fedi.service.TweetService;
import com.fedi.web.dto.ResultDto;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TweetController {
    private final TweetService tweetService;
    private final ResultService resultService;

    @PatchMapping("/tweets/{tweetId}/report")
    public String reportTweet(@PathVariable Long tweetId){
        return tweetService.reportTweet(tweetId);
    }
    
    @PatchMapping("/tweets/{tweetId}/suspend")
    public String suspendTweet(@PathVariable Long tweetId) throws IOException {
    	return tweetService.suspendTweet(tweetId);
    	
    }

}

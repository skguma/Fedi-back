package com.fedi.web.dto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ImageRequestDto {
    private String accountId;
    private String accountName;
    private String tweetUrl;
    private String imageUrl;
    
    @ConstructorProperties({"accountId", "accountName", "tweetUrl", "imageUrl"})
    public ImageRequestDto(String accountId, String accountName, String tweetUrl, String imageUrl) {
    	this.accountId = accountId;
    	this.accountName = accountName;
    	this.tweetUrl = tweetUrl;
    	this.imageUrl = imageUrl;
    }
}

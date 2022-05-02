package com.fedi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class MailRequestDto {
    private String recipient;
    private ArrayList<String> tweetUrls;
    private String networkUrl;

    @Builder
    public MailRequestDto(String recipient, ArrayList<String> tweetUrls, String networkUrl){
        this.recipient= recipient;
        this.tweetUrls = tweetUrls;
        this.networkUrl = networkUrl;
    }
}

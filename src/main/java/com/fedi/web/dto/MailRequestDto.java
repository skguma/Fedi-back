package com.fedi.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class MailRequestDto {
    private String recipient;
    private ArrayList<String> tweetUrls;

    public MailRequestDto(String recipient, ArrayList<String> tweetUrls){
        this.recipient= recipient;
        this.tweetUrls = tweetUrls;
    }
}

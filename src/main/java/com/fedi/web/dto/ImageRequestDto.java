package com.fedi.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageRequestDto {
    private String accountId;
    private String accountName;
    private String tweetUrl;
}

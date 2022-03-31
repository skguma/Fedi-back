package com.fedi.service;

import com.fedi.domain.entity.Account;
import com.fedi.domain.entity.Image;
import com.fedi.domain.entity.Tweet;
import com.fedi.domain.repository.AccountRepository;
import com.fedi.domain.repository.ImageRepository;
import com.fedi.domain.repository.TweetRepository;
import com.fedi.web.dto.ImageRequestDto;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final AccountRepository accountRepository;
    private final ImageRepository imageRepository;
    private final TweetRepository tweetRepository;
    private final S3Service s3Service;
    

    @Transactional
    public String upload(List<MultipartFile> images, ImageRequestDto requestDto) throws IllegalArgumentException, IOException {
        checkParameterValues();

        for(MultipartFile image : images){
            if(isAccountExist(requestDto.getAccountId)){

            }else{
                Account account = Account.builder()
                        .accountId(requestDto.getAccountId())
                        .accountName(requestDto.getAccountName())
                        .build();

                accountRepository.save(account);
            }

            Tweet tweet = Tweet.builder()
                    .account(account)
                    .tweetUrl(requestDto.getTweetUrl())
                    .reportFlag(false)
                    .build();

            tweetRepository.save(tweet);

            String imageUrl = s3Service.upload(image);

            Image entity = Image.builder()
                    .tweet(tweet)
                    .imageUrl(imageUrl)
                    .build();

            imageRepository.save(entity);
        }

        return "success";
    }
    
    @Transactional(readOnly = true)
    public String getModelRequest() {
    	List<Image> images = imageRepository.findAll();
    	JSONObject jsonObj = new JSONObject();
    	for (Image image : images) {
//    		jsonObj.put(image.getImageId(), image.getImageUrl());
    		jsonObj.put(image.getImageId(), image.getVector()); // vector
    	}
    	return jsonObj.toJSONString();
    }
}

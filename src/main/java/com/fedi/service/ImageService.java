package com.fedi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.domain.entity.Account;
import com.fedi.domain.entity.Image;
import com.fedi.domain.entity.Tweet;
import com.fedi.domain.repository.AccountRepository;
import com.fedi.domain.repository.ImageRepository;
import com.fedi.domain.repository.TweetRepository;
import com.fedi.web.dto.ImageRequestDto;
import com.fedi.web.dto.VectorResponseDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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
        if(images.get(0).isEmpty()){
            throw new IllegalArgumentException("image is null");
        }

        if(requestDto.getAccountId() == null || requestDto.getAccountName() == null || requestDto.getTweetUrl() == null){
            throw new IllegalArgumentException("required parameter is null");
        }

        if(requestDto.getAccountId().isEmpty() || requestDto.getAccountName().isEmpty() || requestDto.getTweetUrl().isEmpty()){
            throw new IllegalArgumentException("required parameter is empty");
        }

        for(MultipartFile image : images){
            Account account = Account.builder()
                        .accountId(requestDto.getAccountId())
                        .accountName(requestDto.getAccountName())
                        .build();

            accountRepository.save(account);

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
    
    public List<VectorResponseDto> extractVector(List<ImageRequestDto> requestDto) throws ParseException, JsonMappingException, JsonProcessingException {
    	HashMap<Integer,String> images = new HashMap<>();
    	int index = 0;
    	
    	for (ImageRequestDto dto : requestDto) {
    		images.put(index, dto.getImageUrl());
    		index++;
    	}
    	
    	RestTemplate restTemplate = new RestTemplate();
		
		JSONParser jsonParser = new JSONParser();
		String url = "http://localhost:5000/extract";
		
		HttpHeaders httpHeaders = new HttpHeaders(); //Header 생성
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		map.add("images", gson.toJson(images));
		
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);
		HttpEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		List<VectorResponseDto> vectors = new ObjectMapper().readValue(response.getBody().toString(), new TypeReference<List<VectorResponseDto>>(){});
		
		return vectors;
    }
    
    @Transactional
    public String uploadImages(List<ImageRequestDto> images, List<VectorResponseDto> vectors) {
    	int count = 0;
    	for (VectorResponseDto vector : vectors) {
    		int index = vector.getIndex();
    		ImageRequestDto image = images.get(index);
    		
    		Account account = Account.builder()
                    .accountId(image.getAccountId())
                    .accountName(image.getAccountName())
                    .build();

	        accountRepository.save(account);
	
	        Tweet tweet = Tweet.builder()
	                .account(account)
	                .tweetUrl(image.getTweetUrl())
	                .reportFlag(false)
	                .suspendFlag(false)
	                .build();
	
	        tweetRepository.save(tweet);
	
	        Image entity = Image.builder()
	                .tweet(tweet)
	                .imageUrl(image.getImageUrl())
	                .vector(vector.getVector())
	                .eyes(vector.getEyes())
	                .size(vector.getSize())
	                .build();
	
	        imageRepository.save(entity);
	        count++;
    		
    	}
    	return "success: "+ String.valueOf(count);
    }
    
    @Transactional(readOnly = true)
    public String getModelRequest() {
    	List<Image> images = imageRepository.findAll();
    	JSONObject jsonObj = new JSONObject();
    	for (Image image : images) {
    		jsonObj.put(image.getImageId(), image.getVector()); // vector
    	}
    	return jsonObj.toJSONString();
    }
}

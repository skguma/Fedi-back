package com.fedi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.service.ImageService;
import com.fedi.web.dto.ImageRequestDto;
import com.fedi.web.dto.LikeRpaResponseDto;
import com.fedi.web.dto.VectorResponseDto;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ImageController {
    private final ImageService imageService;
    
    @PostMapping("/images")
    public ResponseEntity<Object> upload(@RequestParam(value="requestDto") String requestDtoString) {
    	ObjectMapper mapper = new ObjectMapper();
    	requestDtoString = requestDtoString.replaceAll("\\\\", "");
    	try {
    		List<ImageRequestDto> requestDto = mapper.readValue(requestDtoString, new TypeReference<List<ImageRequestDto>>() {});
    		for (ImageRequestDto dto : requestDto) {
    			System.out.println(dto.getImageUrl());
    		}
			List<VectorResponseDto> responseDto = imageService.extractVector(requestDto);
			System.out.println(responseDto.get(0).getVector());
			return ResponseEntity.status(HttpStatus.CREATED).body(imageService.uploadImages(requestDto, responseDto));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}

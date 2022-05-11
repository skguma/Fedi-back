package com.fedi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.service.ImageService;
import com.fedi.web.dto.ImageRequestDto;
import com.fedi.web.dto.VectorResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {
    private final ImageService imageService;
    
    @PostMapping("/images")
    public ResponseEntity<Object> upload(@RequestBody List<ImageRequestDto> requestDto) {
    		
		List<VectorResponseDto> responseDto;
		try {
			responseDto = imageService.extractVector(requestDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(imageService.uploadImages(requestDto, responseDto));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}

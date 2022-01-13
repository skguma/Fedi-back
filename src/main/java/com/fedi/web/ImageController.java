package com.fedi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.service.ImageService;
import com.fedi.web.dto.ImageRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/images")
    public ResponseEntity<Object> upload(@RequestParam(value="images")List<MultipartFile> images, @RequestParam(value="requestDto") String requestDtoString){
        try{
            ImageRequestDto requestDto = new ObjectMapper().readValue(requestDtoString, ImageRequestDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageService.upload(images, requestDto));
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.fedi.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fedi.service.ResultService;
import com.fedi.web.dto.ResultDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ResultController {
	private final ResultService resultService;
	private final Double threshold = 95.0; //threshold
	
	@PostMapping(value="/results", consumes = {"multipart/form-data"})
	public List<ResultDto> getResults(@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println(file.getOriginalFilename());	
		
		return resultService.searchGreatherThan(threshold);
		
		}
}

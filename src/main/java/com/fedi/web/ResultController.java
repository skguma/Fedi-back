package com.fedi.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	public void getResults(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
		System.out.println(file.getOriginalFilename());	
		
		String eyes = resultService.flaskTest(file);
		System.out.println(eyes);
		
		response.sendRedirect("/results/view");
		
	}
	
	@GetMapping(value="/results/view")
	public List<ResultDto> getResults(){
		return resultService.searchGreatherThan(threshold);
	}
}

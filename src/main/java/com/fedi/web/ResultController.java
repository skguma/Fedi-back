package com.fedi.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.domain.entity.Image;
import com.fedi.service.AnalysisService;
import com.fedi.service.ImageService;
import com.fedi.service.ResultService;
import com.fedi.web.dto.ModelRequestDto;
import com.fedi.web.dto.ModelResponseDto;
import com.fedi.web.dto.ResultDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ResultController {
	private final ResultService resultService;
	private final ImageService imageService;
	private final AnalysisService analysisService;
	private final Double threshold = 95.0; //threshold
	
	@PostMapping(value="/results", consumes = {"multipart/form-data"})
	public void getResults(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		
		String images = imageService.getAllJsonString();
		System.out.println(images);
		
		JSONObject analysis = resultService.getAnalysis(file, images);
		Double inputVector = (Double) analysis.get("inputVector");
		String modelResponse = (String) analysis.get("analysis");
		List<ModelResponseDto> dtos = objMapper.readValue(modelResponse, new TypeReference<List<ModelResponseDto>>(){});
		
		analysisService.addAnalysis(dtos, inputVector);
		
		response.sendRedirect("/results/view");
		
	}
	
	@GetMapping(value="/results/view")
	public List<ResultDto> getResults(){
		return resultService.searchGreatherThan(threshold);
	}
}

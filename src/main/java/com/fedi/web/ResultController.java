package com.fedi.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.service.AnalysisService;
import com.fedi.service.ImageService;
import com.fedi.service.ResultService;
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
	
	@PostMapping(value="/results", consumes = {"multipart/form-data"})
	public List<ResultDto> getResults(@RequestParam("file") MultipartFile file) {
		ObjectMapper objMapper = new ObjectMapper();
		
		String images = imageService.getModelRequest();
		
		JSONObject analysis;
		Map<String, Double> map = null;
		String inputVector = "";
		try {
			analysis = resultService.getAnalysis(file, images);
			inputVector = (String) analysis.get("inputVector");
			JSONObject modelResponse = (JSONObject) analysis.get("analysis");
			map = objMapper.readValue(modelResponse.toString(), Map.class);
		} catch (ParseException | JsonProcessingException e) {
			e.printStackTrace();
		}
		
		List<Long> analysisIds = analysisService.addAnalysis(map, inputVector);
		return resultService.searchGreatherThan(analysisIds);
		
	}
	
}

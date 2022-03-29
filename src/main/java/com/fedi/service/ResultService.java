package com.fedi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fedi.domain.repository.AnalysisRepository;
import com.fedi.web.dto.ResultDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ResultService {
	
	@Value("${threshold}")
	private Double threshold;
	
	private final AnalysisRepository analysisRepository;
	
	@Transactional(readOnly = true)
	public List<ResultDto> searchGreatherThan(List<Long> analysisIds){
		return analysisRepository.findGreaterThan(threshold, analysisIds).stream()
				.map(ResultDto::new)
				.collect(Collectors.toList());
	}
	
	public JSONObject getAnalysis(MultipartFile file, String images) throws ParseException {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		JSONParser jsonParser = new JSONParser();
		String url = "http://8971-221-147-94-92.ngrok.io/analysis";
		
		HttpHeaders httpHeaders = new HttpHeaders(); //Header 생성
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", file.getResource());
		map.add("images", images);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);
		HttpEntity<String> response = restTemplate.postForEntity(url, request, String.class); // call api
		
		JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody().toString());
		return jsonObj;
	}
}

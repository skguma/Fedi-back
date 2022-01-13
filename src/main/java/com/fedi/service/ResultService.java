package com.fedi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fedi.domain.repository.AnalysisRepository;
import com.fedi.web.dto.ResultDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ResultService {
	
	private final AnalysisRepository analysisRepository;
	
	@Transactional(readOnly = true)
	public List<ResultDto> searchGreatherThan(Double threshold){
		return analysisRepository.findGreaterThan(threshold).stream()
				.map(ResultDto::new)
				.collect(Collectors.toList());
	}
}

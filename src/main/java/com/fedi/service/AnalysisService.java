package com.fedi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fedi.domain.entity.Analysis;
import com.fedi.domain.entity.Image;
import com.fedi.domain.repository.AnalysisRepository;
import com.fedi.domain.repository.ImageRepository;
import com.fedi.web.dto.ModelResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisService {
	private final AnalysisRepository analysisRepository;
	private final ImageRepository imageRepository;
	
	public List<Long> addAnalysis(Map<Long, Double> dtos, String inputVector) {
		List<Long> analysisIds = new ArrayList<>();
		for (Map.Entry<Long, Double> dto : dtos.entrySet()) {
			Image image = imageRepository.findById(dto.getKey())
					.orElseThrow(() -> new IllegalArgumentException("no image"));
			Analysis analysis = Analysis.builder()
					.image(image)
					.similarity(dto.getValue())
					.inputVector(inputVector).build();
			
			analysisIds.add(analysisRepository.save(analysis).getAnalysisId());
		}
		return analysisIds;
	}
}

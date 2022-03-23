package com.fedi.service;

import java.util.List;

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
	
	public void addAnalysis(List<ModelResponseDto> dtos, Double inputVector) {
		for (ModelResponseDto dto : dtos) {
			Image image = imageRepository.findById(dto.getImageId())
					.orElseThrow(() -> new IllegalArgumentException("no image"));
			Analysis analysis = Analysis.builder()
					.image(image)
					.similarity(dto.getSimilarity())
					.inputVector(inputVector).build();
			
			analysisRepository.save(analysis);
		}
	}
}

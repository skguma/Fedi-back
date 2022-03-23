package com.fedi.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ModelResponseDto {
	private Long imageId;
	private Double similarity;
	
	@Builder
	public ModelResponseDto(Long imageId, Double similarity) {
		this.imageId = imageId;
		this.similarity = similarity;
	}
}

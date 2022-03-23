package com.fedi.web.dto;

import com.fedi.domain.entity.Image;

import lombok.Builder;

public class ModelRequestDto {
	private Long imageId;
	private Double vector;
	
	@Builder
	public ModelRequestDto(Image image) {
		this.imageId = image.getImageId();
		this.vector = image.getVector();
	}
}

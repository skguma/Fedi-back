package com.fedi.web.dto;

import com.fedi.domain.entity.Image;

import lombok.Builder;

public class ModelRequestDto {
	private Long imageId;
//	private Double vector;
	private String imageUrl;
	
	@Builder
	public ModelRequestDto(Image image) {
		this.imageId = image.getImageId();
//		this.vector = image.getVector();
		this.imageUrl = image.getImageUrl();
	}
}

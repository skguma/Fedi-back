package com.fedi.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRpaResponseDto {
	private String name;
	private String url;
	private String id;
	
	public LikeRpaResponseDto(String name, String url, String id) {
		this.name = name;
		this.url = url;
		this.id = id;
	}
}

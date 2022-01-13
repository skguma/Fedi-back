package com.fedi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkDto {
	private int source;
	private int target;
	
	@Builder
	public LinkDto(int source, int target) {
		this.source = source;
		this.target = target;
	}
}

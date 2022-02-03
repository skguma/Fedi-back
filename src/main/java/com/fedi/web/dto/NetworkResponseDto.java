package com.fedi.web.dto;

import java.util.ArrayList;

import com.fedi.domain.entity.Tweet;

import lombok.Getter;

@Getter
public class NetworkResponseDto {

	private ArrayList<NodeDto> nodes;
	private ArrayList<LinkDto> links;
	
	public NetworkResponseDto(ArrayList<NodeDto> nodes, ArrayList<LinkDto> links) {
		this.nodes = nodes;
		this.links = links;
	}
}

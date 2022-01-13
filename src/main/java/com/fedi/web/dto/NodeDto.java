package com.fedi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDto {
	private int id;
	private String name;
	private String accountId;
	private int group;
	private int value;
	private String url;
	
	@Builder
	public NodeDto(int id, String name, String accountId, int group, int value, String url) {
		this.id = id;
		this.name = name;
		this.accountId = accountId;
		this.group = group;
		this.value = value;
		this.url = url;
	}
}

package com.fedi.web.dto;

import java.beans.ConstructorProperties;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeRpaResponseDto {
	private String accountName;
	private String url;
	private String accountId;
	private String sourceId;
	
	@ConstructorProperties({"accountName", "url", "accountId", "sourceId"})
	public LikeRpaResponseDto(String accountName, String url, String accountId, String sourceId) {
		this.accountName = accountName;
		this.url = url;
		this.accountId = accountId;
		this.sourceId = sourceId;
	}
}

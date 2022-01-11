package com.fedi.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fedi.service.NetworkService;
import com.fedi.web.dto.NetworkResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NetworkController {

	private final NetworkService networkService;
	
	@GetMapping("/networks")
	public List<NetworkResponseDto> getNetworks(@RequestBody Map<String, List<Long>> tweetInfo){
		
		List<Long> tweetIds =tweetInfo.get("tweetId");
		return networkService.searchAllById(tweetIds);
	}
	
}

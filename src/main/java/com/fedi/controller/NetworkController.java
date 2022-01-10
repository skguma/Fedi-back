package com.fedi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fedi.dto.NetworkResponseDto;
import com.fedi.entity.Tweet;
import com.fedi.service.NetworkService;

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

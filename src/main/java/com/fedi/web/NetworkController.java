package com.fedi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.domain.entity.Tweet;
import com.fedi.service.NetworkService;
import com.fedi.service.RpaAuthService;
import com.fedi.web.dto.LikeRpaRequestDto;
import com.fedi.web.dto.LinkDto;
import com.fedi.web.dto.NetworkResponseDto;
import com.fedi.web.dto.NodeDto;
import com.fedi.web.dto.LikeRpaResponseDto;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NetworkController {

	private final NetworkService networkService;
	private final RpaAuthService rpaAuthService;
	
	@GetMapping("/networks")
	public NetworkResponseDto getNetworks(@RequestBody Map<String, List<Long>> tweetInfo) throws Exception{
		
		List<Long> tweetIds = tweetInfo.get("tweetId"); // tweetIds = [1, 2]
		
		List<Tweet> tweets = networkService.findTweetsbyId(tweetIds);
		List<LikeRpaRequestDto> requestDto = networkService.extractUrl(tweets);
		
		Map<String, List<LikeRpaRequestDto>> map = new HashMap();
		map.put("InputArguments", requestDto);
		
		
		String inputArguments = new Gson().toJson(map);
		
		// rpa 호출
		// 1. get access_token
		String access_token = rpaAuthService.requestAuth();
//		System.out.println("access_token: " + access_token);
		
		// 2. get releaseKey
		String releaseKey = rpaAuthService.getReleaseKey(access_token);
//		System.out.println("Key : "+releaseKey);

		// 3. call RPA and get responseId
		Long responseId = rpaAuthService.callRpa(access_token, inputArguments);
//		System.out.println("Id : " + Long.toString(respondeId));
//		TimeUnit.SECONDS.sleep(1);

		// 4. get OutputArguments using responseId
		List<LikeRpaResponseDto> rpaResponse = rpaAuthService.getOutput(responseId, access_token);
		
		for (LikeRpaResponseDto e : rpaResponse) {
			System.out.println(e.getAccountName());
		}
		
		ArrayList<NodeDto> nodeDtos = new ArrayList<>();
		ArrayList<LinkDto> linkDtos = new ArrayList<>();
		
		// source node의 accountid와 id 맵
		Map<String, Integer> sourceIdMap = new HashMap<>();
		
		int id = 1;
		
		// source node 먼저 추가 (사용자가 넘겨준 트윗Id에 해당하는 정보)
		for (Tweet tweet : tweets) {
			sourceIdMap.put(tweet.getAccount().getAccountId(), id);
			
			NodeDto node = NodeDto.builder()
					.id(id)
					.name(tweet.getAccount().getAccountName())
					.accountId(tweet.getAccount().getAccountId())
					.group(id)
					.value(1)
					.url(tweet.getTweetUrl()).build();
			nodeDtos.add(node);
			id += 1;
		}
		
		// rpa에게 받은 node , link 추가
		for (LikeRpaResponseDto dto : rpaResponse) {
			int group = sourceIdMap.get(dto.getSourceId());
			NodeDto node = NodeDto.builder()
					.id(id)
					.name(dto.getAccountName())
					.accountId(dto.getAccountId())
					.group(group)
					.value(0)
					.url(dto.getUrl()).build();
			nodeDtos.add(node);
			
			LinkDto link = LinkDto.builder()
					.source(group)
					.target(id).build();
			linkDtos.add(link);
			
			id += 1;
		}
		
		NetworkResponseDto networks = NetworkResponseDto.builder()
				.nodes(nodeDtos)
				.links(linkDtos).build();
		
		return networks;
	}
	
}

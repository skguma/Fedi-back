package com.fedi.web;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fedi.domain.entity.Tweet;
import com.fedi.service.NetworkService;
import com.fedi.web.dto.LikeRpaRequestDto;
import com.fedi.web.dto.LikeRpaResponseDto;
import com.fedi.web.dto.NetworkResponseDto;
import com.fedi.web.dto.NodeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NetworkController {

	private final NetworkService networkService;
	
	@GetMapping("/networks")
	public List<NetworkResponseDto> getNetworks(@RequestBody Map<String, List<Long>> tweetInfo){
		
		List<Long> tweetIds = tweetInfo.get("tweetId"); // tweetIds = [1, 2]
		
		List<LikeRpaRequestDto> requestDto = networkService.findAllbyId(tweetIds); //rpa 호출 시 같이 보낼 데이터 - 사용자가 선택한 트윗ID+URL(가공)
		
// -----------------------------------------------------------------------------		
//		좋아요/리트윗 스크랩핑 rpa api 호출 -- 하나의 트윗에 대한 응답
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		
//		HttpEntity<List<LikeRpaRequestDto>> entity = new HttpEntity<>(requestDto, headers);
//		
//		RestTemplate rt = new RestTemplate();
//		
//		
//		ResponseEntity<LikeRpaResponseDto[]> responseDto = rt.exchange(
//				"https://{요청할 서버 주소}",
//				HttpMethod.GET,
//				entity,
//				LikeRpaResponseDto[].class
//				);
//				
//		List<LikeRpaResponseDto> response = Arrays.asList(responseDto.getBody());
//----------------------------------------------------------------------------
		List<NetworkResponseDto> networks = new ArrayList<>();
		
		/*
		 * 1. nodes에 원본트윗에 해당하는 계정 추가 - id, group ++ , value=1
		 * 2. rpa에게 받은 response..를 추가
		 * 	- nodes와 links 둘 다 동시에 추가
		 * 	- nodes: id는 위에서 추가했던 id에서 그대로 증가하면서.. value=0, group은 해당하는 원본트윗의 id(=group) 그대로
		 * 	- links: 원본트윗 id가 source, 해당 계정의 id가 target
		 */
		
		
		return networks;
	}
	
	
}

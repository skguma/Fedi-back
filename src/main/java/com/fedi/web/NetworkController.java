package com.fedi.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedi.domain.entity.Tweet;
import com.fedi.service.MailService;
import com.fedi.service.NetworkService;
import com.fedi.service.RpaAuthService;
import com.fedi.service.TweetService;
import com.fedi.web.dto.RpaRequestDto;
import com.fedi.web.dto.LinkDto;
import com.fedi.web.dto.MailRequestDto;
import com.fedi.web.dto.NetworkResponseDto;
import com.fedi.web.dto.NodeDto;
import com.fedi.web.dto.LikeRpaResponseDto;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NetworkController {
	
	@Value("${spring.rpa.releaseKeyNetwork}")
	private String releaseKeyNetwork;
	
	@Value("${spring.rpa.releaseKeyReport}")
	private String releaseKeyReport;

	private final NetworkService networkService;
	private final RpaAuthService rpaAuthService;
	private final MailService mailService;
	private final TweetService tweetService;
	
	@PostMapping("/networks")
	public ResponseEntity<Object> getNetworks(@RequestBody Map<String, Object> imageInfo) {
		ObjectMapper objMapper = new ObjectMapper();
		String email = (String) imageInfo.get("email");
		
		List<Object> imageIds = (List<Object>) imageInfo.get("imageId");
		List<Long> ids = imageIds.stream().map(x -> Long.parseLong(String.valueOf(x))).collect(Collectors.toList());
		List<Tweet> tweets = tweetService.findTweets(ids);
		List<RpaRequestDto> requestDto = tweets.stream().map(RpaRequestDto::new).collect(Collectors.toList());

		Map<String, List<RpaRequestDto>> map = new HashMap();
		map.put("InputArguments", requestDto);
			
		String inputArguments = new Gson().toJson(map);
		
		// Network rpa 호출
		String access_token;
		List<LikeRpaResponseDto> rpaResponse = new ArrayList<>();
		
		try {
			// 1. get access_token: 24시간마다 갱신
			access_token = rpaAuthService.requestAuth();
			System.out.println("access_token: " + access_token); //
			
			// 2. call RPA and get responseId
			Long responseId = rpaAuthService.callRpa(access_token, inputArguments, releaseKeyNetwork);
			System.out.println("Id : " + Long.toString(responseId)); //
			
			// 3. get OutputArguments using responseId
			String networkOutput = rpaAuthService.getOutput(responseId, access_token);
			System.out.println(networkOutput); //
			rpaResponse = objMapper.readValue(networkOutput, new TypeReference<List<LikeRpaResponseDto>>(){});
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Like RPA failed");
		}
		
		// Tweet entity에 retweets 추가.
		List<RpaRequestDto> reportReq = networkService.updateRetweets(rpaResponse, tweets);
		System.out.println("Update Success");
		
		// 메일 발송
		String networkUrl ="http://localhost:8080/retweets/" + ids.stream().map(s -> String.valueOf(s)).collect(Collectors.joining(","));
		ArrayList<String> url = (ArrayList<String>) requestDto.stream().map(RpaRequestDto::getTweetUrl).collect(Collectors.toList());
		MailRequestDto mailReq = new MailRequestDto(email, url, networkUrl);
		
		try {
			mailService.sendMail(mailReq);
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mail sending failed");
		}
		
		System.out.println("mail sending success");
		
		// 신고 rpa 호출
		List<String> urls = networkService.extractUrl(tweets); // 계정 url 추출.
		reportReq.addAll(urls.stream().map(RpaRequestDto::new).collect(Collectors.toList()));
		JSONObject input = new JSONObject();
		input.put("InputArguments", new Gson().toJson(reportReq));
		input.put("Email", email);
		String reportInputArg = input.toJSONString();
		Long reportResId;
		try {
			reportResId = rpaAuthService.callRpa(access_token, reportInputArg, releaseKeyReport);
			System.out.println(reportResId); //
			String reportOutput = rpaAuthService.getOutput(reportResId, access_token);
			System.out.println(reportOutput); //
			if (!reportOutput.equals("success")) {
				throw new Exception();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Report RPA failed");
		}
		
		List<Long> tweetIds = tweets.stream().map(Tweet::getTweetId).collect(Collectors.toList());	
		System.out.println(tweetService.reportTweets(tweetIds));
		return ResponseEntity.ok().body("success");

	}
	

	@GetMapping("/retweets/{imageId}")
	public NetworkResponseDto getRetweets(@PathVariable Long[] imageId){
		List<Long> imageIds = Arrays.asList(imageId);
		List<Tweet> tweets = tweetService.findTweets(imageIds);
		ObjectMapper objMapper = new ObjectMapper();
		JSONParser jsonParser = new JSONParser();
		List<LikeRpaResponseDto> resDto = new ArrayList();
		
		ArrayList<NodeDto> nodeDtos = new ArrayList<>();
		ArrayList<LinkDto> linkDtos = new ArrayList<>();

		int id = 0;
		int sourceId = 0;
		int group = 0;
		for (Tweet tweet : tweets) {
			sourceId = ++id;
			NodeDto node = NodeDto.builder()
					.id(id)
					.name(tweet.getAccount().getAccountName())
					.accountId(tweet.getAccount().getAccountId())
					.group(++group)
					.value(1)
					.url(tweet.getTweetUrl()).build();
			nodeDtos.add(node);
			
			JSONObject outputObj;
			try {
				outputObj = (JSONObject)jsonParser.parse(tweet.getRetweets());
				JSONArray jr = (JSONArray)outputObj.get("retweets");
				resDto = objMapper.readValue(jr.toJSONString(), new TypeReference<List<LikeRpaResponseDto>>(){});
			} catch (ParseException | JsonProcessingException e) {
				e.printStackTrace();
			}

			for (LikeRpaResponseDto dto : resDto) {
				id++;
				nodeDtos.add(NodeDto.builder()
						.id(id)
						.name(dto.getAccountName())
						.accountId(dto.getAccountId())
						.group(group)
						.value(0)
						.url(dto.getUrl()).build());
				
				LinkDto link = LinkDto.builder()
						.source(sourceId)
						.target(id).build();
				linkDtos.add(link);
			}
			
		}
	
		NetworkResponseDto networks = NetworkResponseDto.builder()
				.nodes(nodeDtos)
				.links(linkDtos).build();
		
		return networks;
	}
	
}

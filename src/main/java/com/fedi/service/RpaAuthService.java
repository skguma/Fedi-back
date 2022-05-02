package com.fedi.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RpaAuthService {
	
	@Value("${spring.rpa.userKey}")
    private String userKey;
	
	@Value("${spring.rpa.clientId}")
	private String clientId;
	
	@Value("${spring.rpa.tenantName}")
	private String tenantName;
	
	@Value("${spring.rpa.folderId}")
	private String folderId;
	
	static JSONParser jsonParser = new JSONParser();
	
	public String requestAuth() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://account.uipath.com/oauth/token";
		
		HttpHeaders httpHeaders = new HttpHeaders(); //Header 생성
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject body = new JSONObject();
		body.put("grant_type", "refresh_token");
		body.put("client_id", clientId);
		body.put("refresh_token", userKey);
		
		HttpEntity<?> request = new HttpEntity<>(body.toString(), httpHeaders);
		
		HttpEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		JSONObject jsonObj = (JSONObject)jsonParser.parse(response.getBody().toString());
		String access_token = (String)jsonObj.get("access_token");
		
		return access_token;
	}
	
	public Long callRpa(String access_token, String inputArguments, String releaseKey) throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); //error lob 출력.
		String url = "https://cloud.uipath.com/ncyzdol/DefaultTenant/odata/Jobs/UiPath.Server.Configuration.OData.StartJobs";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("X-UIPATH-TenantName", tenantName);
		httpHeaders.set("Authorization", "Bearer "+access_token);
		httpHeaders.set("X-UIPATH-OrganizationUnitId", folderId);

		JSONObject startInfo = new JSONObject();
		startInfo.put("ReleaseKey", releaseKey);
		startInfo.put("Strategy", "ModernJobsCount");
		startInfo.put("JobsCount", 1);
		startInfo.put("InputArguments", inputArguments);
		
		JSONObject body = new JSONObject();
		body.put("startInfo", startInfo);
		
		HttpEntity<?> request = new HttpEntity<>(body.toString(), httpHeaders);
		System.out.println(request.getBody().toString());
		HttpEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		JSONObject jsonObj = (JSONObject)jsonParser.parse(response.getBody().toString());
		JSONArray value = (JSONArray)jsonObj.get("value");
		JSONObject valueObj = (JSONObject)value.get(0);
		Long id = (Long)valueObj.get("Id");

		return id;
	}
	
	public String getOutput (Long id, String access_token) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); //error lob 출력.
		String url = "https://cloud.uipath.com/ncyzdol/DefaultTenant/odata/Jobs("+Long.toString(id)+")";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("X-UIPATH-TenantName", tenantName);
		httpHeaders.set("Authorization", "Bearer "+access_token);
		httpHeaders.set("X-UIPATH-OrganizationUnitId", folderId);
		
		HttpEntity request = new HttpEntity(httpHeaders);
		String state = "";
		JSONObject jsonObj;
		do {
			HttpEntity<String> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					request,
					String.class
					);
			
			jsonObj = (JSONObject)jsonParser.parse(response.getBody().toString());
			
			System.out.println(response.getBody().toString());
			
			state = (String)jsonObj.get("State");
			
		} while (!state.equals("Successful"));

		if (!state.equals("Successful")) {
			throw new Exception("Network RPA fail");
		}
		
		String outputStr = (String)jsonObj.get("OutputArguments");
		JSONObject outputObj = (JSONObject)jsonParser.parse(outputStr);
		String outputArguments = (String)outputObj.get("OutputArguments");
		
		return outputArguments;
		
		
	}
}

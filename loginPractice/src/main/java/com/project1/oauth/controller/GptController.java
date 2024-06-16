package com.project1.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project1.oauth.domain.ChatGPTRequest;
import com.project1.oauth.domain.ChatGPTResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "GPT-3.5", description = "채팅 관련 API")
@RestController
@RequestMapping("/api/gpt")
public class GptController {
    @Value("${openai.model}")
    private String model; //모델 명 저장

    @Value("${openai.api.url}")
    private String apiURL; //api 주소 저장

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("p1") String prompt1, @RequestParam("p2") String prompt2,@RequestParam("p3") String prompt3){
    	StringBuilder sb = new StringBuilder();
    	sb.append(prompt1).append("의 부위에").append(prompt2).append("을 목적으로 하는").append(prompt3)
    	.append(" 이 시간 내에 하기 적합한 운동 종류 하나랑 이유를 한 줄로 한국어로 답해줘");
    	System.out.println(sb);
        ChatGPTRequest request = new ChatGPTRequest(model, sb.toString()); //리퀘스트 생성 
        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
        System.out.println(chatGPTResponse.getChoices().get(0).getMessage().getContent());
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

}

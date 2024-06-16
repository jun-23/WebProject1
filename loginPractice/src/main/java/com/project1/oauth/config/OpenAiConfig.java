package com.project1.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiConfig {
    @Value("${openai.api.key}")
    private String openAiKey;
    @Bean
    public RestTemplate template(){ //스프링 프레임워크가 제공하는 클래스 자바 객체로 간편이 바꿔줌
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openAiKey); //여기 띄어쓰기를 무조건 해야함
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
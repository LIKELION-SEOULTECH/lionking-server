//package com.example.lionking.domain.blog.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.concurrent.CompletableFuture;
//
//@Service
//@RequiredArgsConstructor
//public class SummaryService {
//    private String SUMMARY_API_URL = "http://lionking-summary:8080/summary";
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Async
//    public CompletableFuture<String> getSummary(String content) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            String requestJson = "{\"text\": \"" + content + "\"}";
//            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
//
//            String rawResponse = restTemplate.postForObject(SUMMARY_API_URL, entity, String.class);
//
//            // JSON 응답에서 "summary" 필드 추출
//            JsonNode root = objectMapper.readTree(rawResponse);
//            String summary = root.path("summary").asText();
//
//            return CompletableFuture.completedFuture(summary);
//        } catch (Exception e) {
//            e.printStackTrace(); // 어떤 예외인지 콘솔에 출력
//            return CompletableFuture.completedFuture("요약하는 과정에서 에러 발생");
//        }
//    }
//}

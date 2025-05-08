package com.kernel.sense_log.domain.ai.service;

import com.kernel.sense_log.domain.ai.dto.AiResDTO;
import com.kernel.sense_log.domain.ai.dto.MakeTagReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAIService{
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private HttpEntity<MakeTagReqDTO> getHttpEntity(MakeTagReqDTO chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        return new HttpEntity<>(chatRequest, headers);
    }
    @Override
    public String makeMessages(String diaryContent) {
        RestTemplate restTemplate = new RestTemplate();
        MakeTagReqDTO request = new MakeTagReqDTO(model, "오늘은 기분이 안좋은 하루였어");

        AiResDTO response = restTemplate.postForObject(apiUrl, getHttpEntity(request), AiResDTO.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new RuntimeException();
        }

        return response.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public void makeTags(String diaryContent) {

    }
}

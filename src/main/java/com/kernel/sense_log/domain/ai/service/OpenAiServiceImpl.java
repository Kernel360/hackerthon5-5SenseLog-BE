package com.kernel.sense_log.domain.ai.service;

import com.kernel.sense_log.common.exception.BaseException;
import com.kernel.sense_log.common.exception.ResultType;
import com.kernel.sense_log.domain.ai.dto.AiResDTO;
import com.kernel.sense_log.domain.ai.dto.MakeMessageReqDTO;
import com.kernel.sense_log.domain.ai.dto.MakeTagReqDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.SubTag;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.repository.SubTagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAIService {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final DiaryRepository diaryRepository;
    private final SubTagRepository subTagRepository;

    private <T> HttpEntity<T> getHttpEntity(T chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        return new HttpEntity<>(chatRequest, headers);
    }

    @Async
    @Transactional
    @Override
    public void makeMessages(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new BaseException(ResultType.DIARY_NOT_FOUND));

        RestTemplate restTemplate = new RestTemplate();


        StringBuilder sb = new StringBuilder();
        sb.append("당신은 마음이 따뜻한 상담가입니다. ")
                .append("당신의 역할은 사용자의 감정을 깊이 공감하고, 위로의 말을 건네는 것입니다. ")
                .append("사용자는 감정이 담긴 한 줄 일기를 남깁니다. ")
                .append("당신은 사용자의 감정을 섬세하게 이해하고, 진심 어린 따뜻한 말로 위로해주세요. ")
                .append("훈계를 하기 보다는, 사용자의 감정을 있는 그대로 받아들이고 공감하며, 가벼운 조언을 제안하는 말투를 사용해주세요. ")
                .append("말투는 다정한 존댓말이며, 친구처럼 다가가되 위엄이 느껴지지 않도록 해주세요.\n\n")
                .append("최대 문장, 한글 기준 70글자 이내 분량으로 위로해주세요. 상대를 지칭할 땐 '당신'이라고 지칭해주세요.\n\n")

                .append("예시:\n")
                .append("[한줄일기]: 오늘도 혼자 밥 먹었어.\n")
                .append("[응답]: 혼자 밥 먹는 시간이 유난히 길게 느껴졌을 것 같아요. 그 외로움을 잘 견뎌낸 당신이 참 대단해요.\n\n")
                .append("[한줄일기]: 하루 종일 눈물이 났어.\n")
                .append("[응답]: 얼마나 마음이 아프셨을까요. 그런 하루를 잘 이겨낸 당신이 참 자랑스러워요. 내일은 행운이 찾아올 거예요\n\n")

                .append("[한줄일기]: ").append(diary.getContent()).append("\n")
                .append("[응답]:");

        MakeMessageReqDTO request = new MakeMessageReqDTO(model, sb.toString());

        AiResDTO response = restTemplate.postForObject(apiUrl, getHttpEntity(request), AiResDTO.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new RuntimeException();
        }
        diaryRepository.updateAiMessage(diaryId, response.getChoices().get(0).getMessage().getContent());
    }

    @Async
    @Transactional
    @Override
    public void makeTags(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new BaseException(ResultType.DIARY_NOT_FOUND));

        RestTemplate restTemplate = new RestTemplate();

        StringBuilder sb = new StringBuilder();
        sb.append("당신은 한 줄의 문장을 보고 1개의 주요 감정과 4개의 부 감정을 길이가 5인 배열로 추출합니다. ")
                .append("주요 감정은 {슬픔},{우울}, {보통}, {기쁨}, {행복}으로 총 5개의 감정이 존재합니다.\n\n")
                .append("부 감정은 {신나는}, {편안한}, {뿌듯한}, {행복한}, {의욕적인}, {상쾌한}, {차분한}, {우울한}, {외로운}, {불안한}, {슬픈}, {짜증나는}, {화난}, {스트레스}, {피곤한}, {기대되는}, {설레는}, {지루한}, {부담되는}, {감사한} 으로 총 20개의 감정이 존재합니다.\n\n")
                .append("결과는 반드시 맨 앞에 주 감정 한 개를 주고, 뒤에 부 감정 4개를 골라서 주세요. 예시: 기쁨, 신나는, 상쾌한, 의욕적인, 편안한\n")


                .append("예시:\n")
                .append("[한줄일기]: 오늘 하루는 너무 지치고 힘들었지만 떡볶이를 먹었더니 기분이 좋아졌어.\n")
                .append("[응답]: 행복한, 피곤한, 스트레스, 편안한, 우울한 \n\n")
                .append("[한줄일기]: 곧 주말이라 너무 신나!\n")
                .append("[응답]: 신나는, 행복한, 편안한, 기대되는, 설레는 \n\n")

                .append("[한줄일기]: ").append(diary.getContent()).append("\n")
                .append("[응답]:");

        MakeTagReqDTO request = new MakeTagReqDTO(model, sb.toString());

        AiResDTO response = restTemplate.postForObject(apiUrl, getHttpEntity(request), AiResDTO.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new RuntimeException();
        }

        String content = response.getChoices().get(0).getMessage().getContent();

        String[] emotions = content.split(",\\s*"); // 쉼표+공백 기준으로 분리

        diaryRepository.updateTag(diaryId, Tag.fromString(emotions[0].trim()));

        for (int i = 1; i < emotions.length; i++) {
            String subEmotion = emotions[i].trim();
            com.kernel.sense_log.domain.entity.enumeration.SubTag subTag = com.kernel.sense_log.domain.entity.enumeration.SubTag.fromString(subEmotion);
            if(subTag != null) subTagRepository.save(SubTag.builder().diaryId(diaryId).subTag(subTag).build());
        }

    }

}

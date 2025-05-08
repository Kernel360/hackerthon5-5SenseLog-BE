package com.kernel.sense_log.domain.ai.service;

public interface OpenAIService {
    void makeMessages(Long diaryId);
    void makeTags(String diaryContent);
}

package com.kernel.sense_log.domain.ai.service;

public interface OpenAIService {
    String makeMessages(String diaryContent);
    void makeTags(String diaryContent);
}

package com.kernel.sense_log.domain.ai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MakeMessageReqDTO {
    private String model;
    private List<MessageDTO> messages;
    private int n;
    private double temperature;

    public MakeMessageReqDTO(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDTO("system", "당신은 공감 능력이 뛰어난 따뜻한 상담사입니다."));
        this.messages.add(new MessageDTO("user", prompt));

        this.n = 1;
        this.temperature = 0.75;
    }
}

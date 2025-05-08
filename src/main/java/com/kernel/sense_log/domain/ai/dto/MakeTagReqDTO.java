package com.kernel.sense_log.domain.ai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MakeTagReqDTO {
    private String model;
    private List<MessageDTO> messages;
    private int n;
    private double temperature;

    public MakeTagReqDTO(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDTO("user", prompt));

        this.n = 1;
        this.temperature = 0.5;
    }
}

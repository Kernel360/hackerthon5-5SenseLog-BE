package com.kernel.sense_log.web.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResDTO {
    private String token;  // Map 대신 String으로 변경

    @Builder
    public TokenResDTO(String token) {
        this.token = token;
    }

    public static TokenResDTO from(Map<String, Object> result) {
        String token = (String) result.get("token");
        return new TokenResDTO(token);
    }
}
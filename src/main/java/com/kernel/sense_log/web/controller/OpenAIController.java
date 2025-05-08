package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.domain.ai.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class OpenAIController {
    // NOTE: 테스트용 컨트롤러입니다. ai 테스트가 완료되면 삭제해주세요.
    public final OpenAIService openAIService;

    @GetMapping
    public String func(){
        return openAIService.makeMessages("");
    }
}

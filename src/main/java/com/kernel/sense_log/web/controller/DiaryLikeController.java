package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.web.dto.DiaryLikeResDTO;
import com.kernel.sense_log.domain.service.DiaryLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries/{diaryId}/likes")
public class DiaryLikeController {

    private final DiaryLikeService diaryLikeService;

    @PostMapping
    public ResponseEntity<DiaryLikeResDTO> reactEmotion(
            @PathVariable Long diaryId,
            @RequestParam Long userId,
            @RequestParam Tag emotion  
    ) {
        System.out.println( emotion);
        DiaryLikeResDTO response = diaryLikeService.saveOrUpdateEmotion(userId, diaryId, emotion);
        return ResponseEntity.ok(response);
    }
}


package com.kernel.sense_log.web.controller;

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

    // ✅ 좋아요 토글 (추가 or 취소)
    @PostMapping
    public ResponseEntity<DiaryLikeResDTO> toggleLike(
            @PathVariable Long diaryId,
            @RequestParam Long userId
    ) {
        DiaryLikeResDTO response = diaryLikeService.toggleLike(userId, diaryId);
        return ResponseEntity.ok(response);
    }

}

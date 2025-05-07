package com.kernel._SenseLog.diaryLike.controller;

import com.kernel._SenseLog.diaryLike.dto.DiaryLikeResponse;
import com.kernel._SenseLog.diaryLike.service.DiaryLikeService;
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
    public ResponseEntity<DiaryLikeResponse> toggleLike(
            @PathVariable Long diaryId,
            @RequestParam Long userId
    ) {
        DiaryLikeResponse response = diaryLikeService.toggleLike(userId, diaryId);
        return ResponseEntity.ok(response);
    }

}

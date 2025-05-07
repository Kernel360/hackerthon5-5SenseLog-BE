package com.kernel._SenseLog.diaryLike.service;

import com.kernel._SenseLog.diaryLike.dto.DiaryLikeResponse;

public interface DiaryLikeService {
    DiaryLikeResponse toggleLike(Long userId, Long diaryId);
}

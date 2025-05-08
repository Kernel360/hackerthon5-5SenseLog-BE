package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.web.dto.DiaryLikeResDTO;

public interface DiaryLikeService {
    DiaryLikeResDTO toggleLike(Long userId, Long diaryId);
}

package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.web.dto.DiaryLikeResDTO;

public interface DiaryLikeService {
    DiaryLikeResDTO saveOrUpdateEmotion(Long userId, Long diaryId, Tag emotion);
}

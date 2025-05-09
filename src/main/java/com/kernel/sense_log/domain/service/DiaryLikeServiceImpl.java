package com.kernel.sense_log.domain.service.impl;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.DiaryLike;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.repository.DiaryLikeRepository;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.web.dto.DiaryLikeResDTO;
import com.kernel.sense_log.domain.service.DiaryLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryLikeServiceImpl implements DiaryLikeService {

    private final DiaryLikeRepository diaryLikeRepository;


    @Override
    public DiaryLikeResDTO saveOrUpdateEmotion(Long userId, Long diaryId, Tag emotion) {
        Optional<DiaryLike> existing = diaryLikeRepository.findByUserIdAndDiaryId(userId, diaryId);

        if (emotion == null) {
            existing.ifPresent(diaryLikeRepository::delete);
            return new DiaryLikeResDTO(userId, diaryId, null); 
        }

        DiaryLike savedLike;

        if (existing.isPresent()) {
            DiaryLike like = existing.get();
            like.setEmoji(emotion);
            savedLike = diaryLikeRepository.save(like);
        } else {
            DiaryLike newLike = new DiaryLike(userId, diaryId, emotion);
            savedLike = diaryLikeRepository.save(newLike);
        }

        return new DiaryLikeResDTO(savedLike.getUserId(), savedLike.getDiaryId(), savedLike.getEmoji());
    }

    public Tag getEmoji(Long userId, Long diaryId) {
        DiaryLike like = diaryLikeRepository.findByUserIdAndDiaryId(userId, diaryId).orElse(null);
        return like == null ? null : like.getEmoji();
    }
}

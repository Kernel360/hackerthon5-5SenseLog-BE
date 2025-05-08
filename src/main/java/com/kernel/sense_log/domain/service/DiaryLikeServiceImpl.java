package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.web.dto.DiaryLikeResDTO;
import com.kernel.sense_log.domain.entity.DiaryLike;
import com.kernel.sense_log.domain.repository.DiaryLikeRepository;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryLikeServiceImpl implements DiaryLikeService {

    private final DiaryLikeRepository diaryLikeRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    @Override
    public DiaryLikeResDTO toggleLike(Long userId, Long diaryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));

        Optional<DiaryLike> existing = diaryLikeRepository.findByUserIdAndDiaryId(userId, diaryId);

        String message;
        if (existing.isPresent()) {
            diaryLikeRepository.delete(existing.get());
            message = "Unliked successfully.";
        } else {
            diaryLikeRepository.save(new DiaryLike(userId, diaryId));
            message = "Liked successfully.";
        }

        return new DiaryLikeResDTO(diary.getId(), user.getId(), message);
    }
}

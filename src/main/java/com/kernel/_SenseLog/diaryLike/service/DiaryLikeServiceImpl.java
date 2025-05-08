package com.kernel._SenseLog.diaryLike.service;

import com.kernel._SenseLog.diary.entity.Diary;
import com.kernel._SenseLog.diary.repository.DiaryRepository;
import com.kernel._SenseLog.diaryLike.dto.DiaryLikeResponse;
import com.kernel._SenseLog.diaryLike.entity.DiaryLike;
import com.kernel._SenseLog.diaryLike.repository.DiaryLikeRepository;
import com.kernel._SenseLog.user.entity.User;
import com.kernel._SenseLog.user.repository.UserRepository;
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
    public DiaryLikeResponse toggleLike(Long userId, Long diaryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));

        Optional<DiaryLike> existing = diaryLikeRepository.findByUserAndDiary(user, diary);

        String message;
        if (existing.isPresent()) {
            diaryLikeRepository.delete(existing.get());
            message = "Unliked successfully.";
        } else {
            diaryLikeRepository.save(new DiaryLike(user, diary));
            message = "Liked successfully.";
        }

        return new DiaryLikeResponse(diary.getId(), user.getId(), message);
    }
}

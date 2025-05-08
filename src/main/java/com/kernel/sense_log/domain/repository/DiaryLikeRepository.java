package com.kernel.sense_log.domain.repository;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.DiaryLike;
import com.kernel.sense_log.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    Optional<DiaryLike> findByUserIdAndDiaryId(Long userId, Long diaryId);
}

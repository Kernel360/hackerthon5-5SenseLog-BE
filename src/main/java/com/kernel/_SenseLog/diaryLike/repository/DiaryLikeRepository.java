package com.kernel._SenseLog.diaryLike.repository;

import com.kernel._SenseLog.diary.entity.Diary;
import com.kernel._SenseLog.diaryLike.entity.DiaryLike;
import com.kernel._SenseLog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    Optional<DiaryLike> findByUserAndDiary(User user, Diary diary);
}

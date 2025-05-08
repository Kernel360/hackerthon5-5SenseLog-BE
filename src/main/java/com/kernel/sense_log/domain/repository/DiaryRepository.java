package com.kernel.sense_log.domain.repository;

import com.kernel.sense_log.domain.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}

package com.kernel.sense_log.domain.repository;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

  Page<Diary> findAllByTag(Tag tagName, Pageable pageable);

  Page<Diary> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

  Page<Diary> findAllByWriterId(Long userId, Pageable pageable);
}

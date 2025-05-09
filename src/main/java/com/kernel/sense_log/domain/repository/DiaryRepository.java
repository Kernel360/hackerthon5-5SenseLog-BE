package com.kernel.sense_log.domain.repository;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

  Page<Diary> findAllByTag(Tag tagName, Pageable pageable);

  Page<Diary> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

  Page<Diary> findAllByWriterId(Long userId, Pageable pageable);

  Page<Diary> findByTagAndCreatedAtBetween(Tag tag, LocalDateTime start, LocalDateTime end, Pageable pageable);

  @Modifying
  @Query("update Diary d set d.aiMessage = :aiMessage where d.id = :id")
  void updateAiMessage(@Param("id") Long id, @Param("aiMessage") String aiMessage);

  @Modifying
  @Query("update Diary d set d.tag = :tag where d.id = :id")
  void updateTag(@Param("id") Long id, @Param("tag") Tag tag);

  Diary findByWriterIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

  Page<Diary> findAllByWriterIdAndCreatedAtBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}

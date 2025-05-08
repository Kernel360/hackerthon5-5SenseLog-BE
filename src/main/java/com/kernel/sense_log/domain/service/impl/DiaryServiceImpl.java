package com.kernel.sense_log.domain.service.impl;

import com.kernel.sense_log.domain.ai.service.OpenAIService;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.repository.SubTagRepository;
import com.kernel.sense_log.domain.service.DiaryService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;
  private final SubTagRepository subTagRepository;
  private final OpenAIService openAIService;
  @Override
  public Diary create(Diary diary) {

    Diary savedDiary = diaryRepository.save(diary);
    openAIService.makeTags(savedDiary.getId());
    openAIService.makeMessages(savedDiary.getId());
    return diaryRepository.save(diary);
  }

  @Override
  public void delete(Long id) {
    diaryRepository.deleteById(id);
  }

  @Override
  public Page<Diary> readAllByTag(Pageable pageable, Tag tag) {
    return diaryRepository.findAllByTag(tag, pageable);
  }

//퍼블릭 ,오블, 모두 조회
  @Override
  public Page<Diary> readAllByCustomDay(Pageable pageable) {
    LocalDateTime now = LocalDateTime.now();
    LocalDate today = now.toLocalDate();

    LocalDate baseDate = now.isBefore(today.atTime(5, 0))
        ? today.minusDays(1)
        : today;

    LocalDateTime start = baseDate.atTime(5, 0);
    LocalDateTime end = baseDate.plusDays(1).atTime(5, 0);
    return diaryRepository.findAllByCreatedAtBetween(start, end, pageable);

  }

  @Override
  public Page<Diary> readAllMyDiary(Long userId, Pageable pageable) {
    return diaryRepository.findAllByWriterId(userId, pageable);
  }


}

package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryService {

  Diary create(Diary diary);

  void delete(Long id);

  Page<Diary> readAllByTag(Pageable pageable, Tag tag);

  Page<Diary> readAllMyDiary(Long userId, Pageable pageable);

  Page<Diary> readAllByCustomDay(Pageable pageable);


}

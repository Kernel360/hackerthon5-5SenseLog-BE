package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.Diary;
import java.util.List;

public interface DiaryService {

  Diary create(Diary diary);

  void delete(Long id);

  List<Diary> readAllByTag();

}

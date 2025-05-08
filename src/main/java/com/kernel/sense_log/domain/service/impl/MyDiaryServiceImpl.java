package com.kernel.sense_log.domain.service.impl;


import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.service.MyDiaryService;
import java.util.List;


public class MyDiaryServiceImpl extends DiaryServiceImpl implements MyDiaryService {


  public MyDiaryServiceImpl(DiaryRepository diaryRepository) {
    super(diaryRepository);
  }

  @Override
  public List<Diary> readAllMyDiary() {
    return List.of();
  }
}

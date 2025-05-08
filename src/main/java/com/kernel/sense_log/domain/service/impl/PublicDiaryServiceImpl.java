package com.kernel.sense_log.domain.service.impl;


import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.repository.UserRepository;
import com.kernel.sense_log.domain.service.PublicDiaryService;
import java.util.List;

public class PublicDiaryServiceImpl extends DiaryServiceImpl implements PublicDiaryService {

  public PublicDiaryServiceImpl(DiaryRepository diaryRepository,
      UserRepository userRepository) {
    super(diaryRepository, userRepository);
  }


  @Override
  public List<Diary> readAllDiary() {
    return List.of();
  }
}

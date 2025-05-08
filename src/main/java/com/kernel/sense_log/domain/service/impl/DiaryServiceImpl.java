package com.kernel.sense_log.domain.service.impl;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.repository.UserRepository;
import com.kernel.sense_log.domain.service.DiaryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;

  private final UserRepository userRepository;

  @Override
  public Diary create(Diary diary) {
    // toDo: ai message, Tag 받기
    diary.addAiMessage("ai message");
    diary.addTag(Tag.기쁨);
    return diaryRepository.save(diary);
  }

  @Override
  public void delete(Long id) {
    diaryRepository.deleteById(id);
  }

  @Override
  public List<Diary> readAllByTag() {
    return List.of();
  }


}

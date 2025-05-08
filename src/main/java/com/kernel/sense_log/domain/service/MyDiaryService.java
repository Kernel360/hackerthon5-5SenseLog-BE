package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.Diary;
import java.util.List;

public interface MyDiaryService extends DiaryService {

  List<Diary> readAllMyDiary();

}

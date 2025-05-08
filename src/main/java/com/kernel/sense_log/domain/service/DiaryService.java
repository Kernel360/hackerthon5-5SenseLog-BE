package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DiaryService {

  Diary create(Diary diary);

  void delete(Long id);

  ResponseDTO<List<DiaryResDto>> readAllByTag(Pageable pageable, Tag tag);

  ResponseDTO<List<DiaryResDto>> readAllMyDiary(Long userId, Pageable pageable);

  ResponseDTO<List<DiaryResDto>> readAllByCustomDay(Pageable pageable);


}

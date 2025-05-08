package com.kernel.sense_log.domain.service.impl;

import static com.kernel.sense_log.web.dto.response.DiaryResDto.toDto;

import com.kernel.sense_log.common.api.Pagination;
import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.repository.UserRepository;
import com.kernel.sense_log.domain.service.DiaryService;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;

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
  public ResponseDTO<List<DiaryResDto>> readAllByTag(Pageable pageable, Tag tag) {
    Page<Diary> diaries = diaryRepository.findAllByTag(tag, pageable);

    var pagination = Pagination.builder()
        .page(diaries.getNumber())
        .size(diaries.getSize())
        .currentElements(diaries.getNumberOfElements())
        .totalElements(diaries.getTotalElements())
        .totalPage(diaries.getTotalPages())
        .build()
        ;

    List<DiaryResDto> dtoDiaries = diaries.stream()
        .map(DiaryResDto::toDto)
        .collect(Collectors.toList());

    return ResponseDTO.ok(dtoDiaries, pagination);
  }


}

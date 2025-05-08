package com.kernel.sense_log.domain.service.impl;

import com.kernel.sense_log.common.api.Pagination;
import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.repository.DiaryRepository;
import com.kernel.sense_log.domain.service.DiaryService;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    return pageToDto(pageable, diaries);
  }


  @Override
  public ResponseDTO<List<DiaryResDto>> readAllByCustomDay(Pageable pageable) {
    LocalDate now = LocalDateTime.now().isBefore(LocalDate.now().atTime(5, 0))
        ? LocalDate.now().minusDays(1)
        : LocalDate.now();

    LocalDateTime start = now.atTime(5, 0);

    LocalDateTime end = now.plusDays(1).atTime(5, 0);

    Page<Diary> diaries = diaryRepository.findAllByCreatedAtBetween(start, end, pageable);

    return pageToDto(pageable, diaries);
  }

  @Override
  public ResponseDTO<List<DiaryResDto>> readAllMyDiary(Long userId, Pageable pageable) {
    Page<Diary> diaries = diaryRepository.findAllByWriterId(userId, pageable);

    return pageToDto(pageable, diaries);
  }
  
  private ResponseDTO<List<DiaryResDto>> pageToDto(Pageable pageable, Page<Diary> diaries) {
    Pagination pagination = Pagination.builder()
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

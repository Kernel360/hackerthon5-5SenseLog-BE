package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.common.api.Pagination;
import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.SubTag;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.service.SubTagService;
import com.kernel.sense_log.domain.service.impl.DiaryServiceImpl;
import com.kernel.sense_log.web.dto.request.DiaryReqDto;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

  private final DiaryServiceImpl diaryService;
  private final SubTagService subTagService;

  @PostMapping
  public ResponseDTO<DiaryResDto> create(
      Long userId,
      @Valid @RequestBody DiaryReqDto diaryRequestDto) {
    Diary diary = diaryService.create(
            DiaryReqDto.toEntity(userId, diaryRequestDto));
    List<SubTag> subTags = subTagService.findAllSubTags(diary.getId());
    return ResponseDTO.ok(DiaryResDto.toDto(diary, subTags));
  }

  @DeleteMapping("/{diaryId}")
  public ResponseDTO<?> delete(
      @PathVariable Long diaryId
  ) {
    diaryService.delete(diaryId);
    return ResponseDTO.ok();
  }

  @GetMapping("/all")
  public ResponseDTO<List<DiaryResDto>> readAllByTag(
      @RequestParam(name = "tag") Tag tag,
      @PageableDefault
      Pageable pageable
  ) {
    return pageToDto(diaryService.readAllByTag(pageable, tag));
  }

  @GetMapping("/all/today")
  public ResponseDTO<List<DiaryResDto>> readAllByCustomDay(
      @PageableDefault
      Pageable pageable) {
    return pageToDto(diaryService.readAllByCustomDay(pageable));
  }

  @GetMapping("/mine")
  public ResponseDTO<List<DiaryResDto>> readAllMyDiary(
      Long myId,
      @PageableDefault
      Pageable pageable
  ) {
    return pageToDto(diaryService.readAllMyDiary(2L, pageable));
  }

  private ResponseDTO<List<DiaryResDto>> pageToDto(Page<Diary> diaries) {
    Pagination pagination = Pagination.builder()
        .page(diaries.getNumber())
        .size(diaries.getSize())
        .currentElements(diaries.getNumberOfElements())
        .totalElements(diaries.getTotalElements())
        .totalPage(diaries.getTotalPages())
        .build()
        ;

    List<DiaryResDto> dtoDiaries = diaries.stream()
            .map(diary -> {
              List<SubTag> subTags = subTagService.findAllSubTags(diary.getId());
                return DiaryResDto.toDto(diary, subTags);
            })
            .collect(Collectors.toList());

    return ResponseDTO.ok(dtoDiaries, pagination);
  }
}

package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.common.api.Pagination;
import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.SubTag;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.service.SubTagService;
import com.kernel.sense_log.domain.service.UserService;
import com.kernel.sense_log.domain.service.impl.DiaryServiceImpl;
import com.kernel.sense_log.web.dto.request.DiaryReqDto;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
  private final UserService userService;

  @PostMapping
  public ResponseDTO<DiaryResDto> create(
      User user,
      @Valid @RequestBody DiaryReqDto diaryRequestDto) {
    Long userId = user.getId();
    Diary diary = diaryService.create(
        DiaryReqDto.toEntity(userId, diaryRequestDto));
    List<SubTag> subTags = subTagService.findAllSubTags(diary.getId());
    return ResponseDTO.ok(DiaryResDto.toDto(diary, Optional.of(user), subTags));
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

  @GetMapping("/all/today/tag")
  public ResponseDTO<List<DiaryResDto>> readAllByTodayAndTag(
          @RequestParam(name = "tag") Tag tag,
          @PageableDefault
          Pageable pageable) {
    return pageToDto(diaryService.readAllByTodayAndTag(pageable, tag));
  }

  @GetMapping("/mine")
  public ResponseDTO<List<DiaryResDto>> readAllMyDiary(
      User user,
      @PageableDefault
      Pageable pageable
  ) {
    Long userId = user.getId();
    return pageToDto(diaryService.readAllMyDiary(userId, pageable));
  }

  @GetMapping("/day")
  public ResponseDTO<List<DiaryResDto>> readDiariesByDay(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @PageableDefault Pageable pageable
  ) {
    return pageToDto(diaryService.readAllByDay(date, pageable));
  }

  @GetMapping("/range")
  public ResponseDTO<List<DiaryResDto>> getDiariesByRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
      @PageableDefault Pageable pageable
  ) {
    return pageToDto(diaryService.readAllByDateRange(start, end, pageable));
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
                return DiaryResDto.toDto(diary, userService.findById(diary.getWriterId()), subTags);
            })
            .collect(Collectors.toList());


    return ResponseDTO.ok(dtoDiaries, pagination);
  }
}
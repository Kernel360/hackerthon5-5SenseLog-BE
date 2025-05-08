package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import com.kernel.sense_log.domain.service.impl.DiaryServiceImpl;
import com.kernel.sense_log.web.dto.request.DiaryReqDto;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

  @PostMapping
  public ResponseDTO<DiaryResDto> create(
//      Long userId,
      @Valid @RequestBody DiaryReqDto diaryRequestDto) {
    return ResponseDTO.ok(DiaryResDto.toDto(diaryService.create(
        DiaryReqDto.toEntity(1L, diaryRequestDto))));
  }

  @DeleteMapping("/{diaryId}")
  public ResponseDTO<?> delete(
      @PathVariable Long diaryId
  ) {
    diaryService.delete(diaryId);
    return ResponseDTO.ok();
  }

  @GetMapping
  public ResponseDTO<List<DiaryResDto>> readAllByTag(
      @RequestParam(name = "tag") Tag tag,
      @PageableDefault
      Pageable pageable
  ) {
    return diaryService.readAllByTag(pageable, tag);
  }
}

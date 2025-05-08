package com.kernel.sense_log.web.dto.response;

import com.kernel.sense_log.common.api.Pagination;
import com.kernel.sense_log.domain.entity.Diary;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryResDto {

  private Long id;
  private String content;
  private Boolean isPrivate;
  private String aiMessage;
  private Long writerId;
  private LocalDateTime createAt;
  private LocalDateTime updateAt;

  public static DiaryResDto toDto(Diary diary) {
    return DiaryResDto.builder().id(diary.getId()).content(diary.getContent())
        .isPrivate(diary.getIsPrivate()).aiMessage(diary.getAiMessage())
        .writerId(diary.getWriterId()).createAt(diary.getCreatedAt()).updateAt(diary.getUpdatedAt())
        .build();
  }

  public static List<DiaryResDto> toDto(Page<Diary> diaries, Pagination pagination) {
    return diaries.stream()
        .map(DiaryResDto::toDto)
        .collect(Collectors.toList());

  }
}
package com.kernel.sense_log.web.dto.request;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.web.dto.response.DiaryResDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryReqDto {

  @NotBlank(message = "내용은 공백일 수 없습니다.")
  @Size(max = 50, message = "내용은 50자 이내여야 합니다.")
  private String content;

  @NotNull(message = "공개 여부는 필수입니다.")
  private Boolean isPrivate;

  public static Diary toEntity(Long writerId, DiaryReqDto diaryReqDto) {
    return Diary.builder().content(diaryReqDto.getContent()).isPrivate(diaryReqDto.getIsPrivate()).writerId(writerId)
        .build();
  }
}
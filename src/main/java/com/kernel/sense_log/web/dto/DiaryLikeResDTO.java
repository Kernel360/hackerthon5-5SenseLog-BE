package com.kernel.sense_log.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryLikeResDTO {
    private Long diaryId;
    private Long userId;
    private String message;
}

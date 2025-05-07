package com.kernel._SenseLog.diaryLike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryLikeResponse {
    private Long diaryId;
    private Long userId;
    private String message;
}

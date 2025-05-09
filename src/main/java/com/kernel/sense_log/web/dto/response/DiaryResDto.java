package com.kernel.sense_log.web.dto.response;

import com.kernel.sense_log.domain.entity.Diary;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kernel.sense_log.domain.entity.SubTag;
import com.kernel.sense_log.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  private String writerNickname;
  private LocalDateTime createAt;
  private LocalDateTime updateAt;
  private String tag;
  private List<String> subTags;
  private Tag myEmoji;

  public static DiaryResDto toDto(Diary diary, Optional<User> user, List<SubTag> originSubTags, Tag myEmoji) {

    List<String> subTags = originSubTags.stream()
            .map(subTag -> subTag.getSubTag().toString())
            .collect(Collectors.toList());
    String tagString = diary.getTag() != null ? diary.getTag().toString() : null;
    String userNickname = user.isEmpty()?"탈퇴한 사용자":user.get().getNickname();
    return DiaryResDto.builder().id(diary.getId()).content(diary.getContent())
        .isPrivate(diary.getIsPrivate()).aiMessage(diary.getAiMessage())
        .writerId(diary.getWriterId()).createAt(diary.getCreatedAt()).updateAt(diary.getUpdatedAt())
            .writerNickname(userNickname)
            .tag(tagString).subTags(subTags).myEmoji(myEmoji)
        .build();
  }

  public static DiaryResDto toDto(Diary diary, Optional<User> user, List<SubTag> originSubTags) {

    List<String> subTags = originSubTags.stream()
        .map(subTag -> subTag.getSubTag().toString())
        .collect(Collectors.toList());
    String tagString = diary.getTag() != null ? diary.getTag().toString() : null;
    String userNickname = user.isEmpty()?"탈퇴한 사용자":user.get().getNickname();
    return DiaryResDto.builder().id(diary.getId()).content(diary.getContent())
        .isPrivate(diary.getIsPrivate()).aiMessage(diary.getAiMessage())
        .writerId(diary.getWriterId()).createAt(diary.getCreatedAt()).updateAt(diary.getUpdatedAt())
        .writerNickname(userNickname)
        .tag(tagString).subTags(subTags)
        .build();
  }

}
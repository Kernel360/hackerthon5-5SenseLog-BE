package com.kernel.sense_log.domain.entity;

import com.kernel.sense_log.common.entity.BaseTimeEntity;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "diary_likes")
@NoArgsConstructor
public class DiaryLike extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Tag emoji;

  @Column(nullable = false)
  private Long userId;


  @Column( nullable = false)
  private Long diaryId;

  public DiaryLike(Long userId, Long diaryId, Tag emoji) {
    this.userId = userId;
    this.diaryId = diaryId;
    this.emoji = emoji;
  }

  public void setEmoji(Tag emoji) {
    this.emoji = emoji;
  }
}


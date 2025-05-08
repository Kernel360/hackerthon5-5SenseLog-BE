package com.kernel.sense_log.domain.entity;

import com.kernel.sense_log.common.entity.BaseTimeEntity;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

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

  // 👉 이 생성자를 꼭 추가해줘
  public DiaryLike(Long userId, Long diaryId) {
    this.userId = userId;
    this.diaryId = diaryId;
  }
}


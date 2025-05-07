package com.kernel._SenseLog.diaryLike.entity;

import com.kernel._SenseLog.common.entity.BaseTimeEntity;
import com.kernel._SenseLog.diary.entity.Diary;
import com.kernel._SenseLog.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diary_likes")
@NoArgsConstructor
public class DiaryLike extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "diary_id", nullable = false)
  private Diary diary;

  // 👉 이 생성자를 꼭 추가해줘
  public DiaryLike(User user, Diary diary) {
    this.user = user;
    this.diary = diary;
  }
}


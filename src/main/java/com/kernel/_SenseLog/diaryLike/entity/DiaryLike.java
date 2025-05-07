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

@Entity
@Table(name = "diary_likes")
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

}

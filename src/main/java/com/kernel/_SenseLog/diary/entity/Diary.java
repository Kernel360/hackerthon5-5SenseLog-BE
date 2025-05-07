package com.kernel._SenseLog.diary.entity;

import com.kernel._SenseLog.common.entity.BaseTimeEntity;
import com.kernel._SenseLog.group.entity.Group;
import com.kernel._SenseLog.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.aspectj.apache.bcel.generic.Tag;

@Entity
@Table(name = "diaries")
public class Diary extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String content;

  private String weather;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "tag_id", nullable = false)
  private Tag tag;

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

}

package com.kernel.sense_log.domain.entity;

import com.kernel.sense_log.common.entity.BaseTimeEntity;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "diaries")
public class Diary extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String content;

  @Column(nullable = false)
  private Boolean isPrivate;

  @Column(length = 255)
  private String aiMessage;

  @Column(nullable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  private Tag tag;

  public void updateAiMessage(String s){
    this.aiMessage = s;
  }

  public void addTag(String s){
    this.tag = Tag.fromString(s);
  }
}

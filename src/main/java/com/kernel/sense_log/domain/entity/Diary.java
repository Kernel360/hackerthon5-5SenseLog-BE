package com.kernel.sense_log.domain.entity;

import com.kernel.sense_log.common.entity.BaseTimeEntity;
import com.kernel.sense_log.domain.entity.enumeration.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
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

  @Column(nullable = false, length = 255)
  private String aiMessage;

  @Column(nullable = false)
  private Long writerId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Tag tag;

  public void addAiMessage(String message) {
    this.aiMessage = message;
  }

  public void addTag(Tag tag) {
    this.tag = tag;
  }

}

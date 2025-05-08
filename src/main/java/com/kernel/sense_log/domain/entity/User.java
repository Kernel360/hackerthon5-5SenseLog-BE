package com.kernel.sense_log.domain.entity;

import com.kernel.sense_log.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(nullable = false, length = 50)
  private String nickname;

  @Builder
  public User(String email, String password, String nickname, Long id) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.id = id;
  }

  public User(String email, String nickname, Long id) {
    this.email = email;
    this.id = id;
    this.nickname = nickname;
  }

  public void encodePassword(String encodePassword) {
    this.password = encodePassword;
  }

  @Builder
  public User(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }

  public void initUserId(Long userId) {
    this.id = userId;
  }
}

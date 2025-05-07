package com.kernel._SenseLog.groupMember.entity;

import com.kernel._SenseLog.common.entity.BaseTimeEntity;
import com.kernel._SenseLog.group.entity.Group;
import com.kernel._SenseLog.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_members")
public class GroupMember extends BaseTimeEntity  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}

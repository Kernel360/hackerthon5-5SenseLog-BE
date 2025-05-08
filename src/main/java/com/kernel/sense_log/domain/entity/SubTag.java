package com.kernel.sense_log.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_tags")
public class SubTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.kernel.sense_log.domain.entity.enumeration.SubTag subTag;

    @Column(nullable = false)
    private Long diaryId;
}

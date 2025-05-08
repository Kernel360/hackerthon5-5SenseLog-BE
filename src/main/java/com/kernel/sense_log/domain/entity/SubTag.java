package com.kernel.sense_log.domain.entity;

import com.kernel.sense_log.domain.entity.enumeration.subEmotion;
import jakarta.persistence.*;

@Entity
@Table(name = "sub_tags")
public class SubTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private subEmotion subEmotion;

    @Column(nullable = false)
    private Boolean isMain;

    @Column(nullable = false)
    private Long diaryId;
}

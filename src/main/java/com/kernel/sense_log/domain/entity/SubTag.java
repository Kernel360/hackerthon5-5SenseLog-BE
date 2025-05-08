package com.kernel.sense_log.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sub_tags")
@Getter
@NoArgsConstructor
public class SubTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.kernel.sense_log.domain.entity.enumeration.SubTag subTag;

    @Column(nullable = false)
    private Long diaryId;

    @Builder
    public SubTag(com.kernel.sense_log.domain.entity.enumeration.SubTag subTag, Long diaryId){
        this.subTag = subTag;
        this.diaryId = diaryId;
    }
}

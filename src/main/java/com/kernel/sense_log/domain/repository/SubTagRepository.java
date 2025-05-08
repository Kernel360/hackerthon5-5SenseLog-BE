package com.kernel.sense_log.domain.repository;

import com.kernel.sense_log.domain.entity.SubTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTagRepository extends JpaRepository<SubTag, Long> {
    List<SubTag> findAllByDiaryId(Long diaryId);
}

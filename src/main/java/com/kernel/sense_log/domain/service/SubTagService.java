package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.SubTag;

import java.util.List;

public interface SubTagService {
    List<SubTag> findAllSubTags(Long diaryId);
}

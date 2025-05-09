package com.kernel.sense_log.domain.service.impl;

import com.kernel.sense_log.domain.entity.SubTag;
import com.kernel.sense_log.domain.repository.SubTagRepository;
import com.kernel.sense_log.domain.service.SubTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTagServiceImpl implements SubTagService {

    private final SubTagRepository subTagRepository;
    @Override
    public List<SubTag> findAllSubTags(Long diaryId) {
        return subTagRepository.findAllByDiaryId(diaryId);
    }
}

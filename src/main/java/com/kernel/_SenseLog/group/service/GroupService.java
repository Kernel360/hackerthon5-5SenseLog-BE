package com.kernel._SenseLog.group.service;

import com.kernel._SenseLog.group.dto.req.CreateGroupReqDto;
import com.kernel._SenseLog.group.dto.req.UpdateGroupReqDto;

public interface GroupService {
    void createCreateGroup(CreateGroupReqDto dto);
    void updateGroup(Long groupId, UpdateGroupReqDto dto);
    void deleteGroup(Long groupId);
}

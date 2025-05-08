package com.kernel._SenseLog.group.service;

import com.kernel._SenseLog.group.dto.req.CreateGroupReqDto;
import com.kernel._SenseLog.group.dto.req.UpdateGroupReqDto;
import com.kernel._SenseLog.group.entity.Group;
import com.kernel._SenseLog.group.repository.GroupRepository;
import com.kernel._SenseLog.user.entity.User;
import com.kernel._SenseLog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    @Override
    public void createCreateGroup(CreateGroupReqDto dto) {
        User user = userRepository.findById(1L).orElseThrow();//TODO 세션에서 파싱
        Group group = Group.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .createdBy(user)
                .build();

        groupRepository.save(group);
    }

    @Override
    public void updateGroup(Long groupId, UpdateGroupReqDto dto) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        group.update(dto.getName(), dto.getDescription());

        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}

package com.kernel._SenseLog.group.controller;

import com.kernel._SenseLog.group.dto.req.CreateGroupReqDto;
import com.kernel._SenseLog.group.dto.req.UpdateGroupReqDto;
import com.kernel._SenseLog.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public void createGroup(@RequestBody CreateGroupReqDto createGroupReqDto){
        groupService.createCreateGroup(createGroupReqDto);
    }

    @PutMapping("/{groupId}")
    public void updateGroup(@PathVariable Long groupId, @RequestBody UpdateGroupReqDto updateReqDto){
        groupService.updateGroup(groupId, updateReqDto);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId){
        groupService.deleteGroup(groupId);
    }
}

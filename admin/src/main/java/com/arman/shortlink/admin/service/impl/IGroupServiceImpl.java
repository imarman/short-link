package com.arman.shortlink.admin.service.impl;

import com.arman.shortlink.admin.utils.RandomGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arman.shortlink.admin.dao.pojo.GroupDo;
import com.arman.shortlink.admin.dao.mapper.GroupMapper;
import com.arman.shortlink.admin.service.IGroupService;

/**
 * @author Arman
 */
@Service
public class IGroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements IGroupService {

    @Override
    public void saveGroup(String groupName) {
        String username = "arman";
        String grid;
        do {
            grid = RandomGenerator.generateRandom();
        } while (hasGrid(grid, username));


        GroupDo groupDo = GroupDo.builder()
                .gid(grid)
                .username(username)
                .name(groupName)
                .build();

        save(groupDo);
    }

    private Boolean hasGrid(String grid, String username) {
        LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getGid, grid)
                .eq(GroupDo::getUsername, username);
        return exists(wrapper);
    }
}

package com.arman.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.arman.shortlink.admin.common.biz.user.UserInfoHolder;
import com.arman.shortlink.admin.dao.mapper.GroupMapper;
import com.arman.shortlink.admin.dao.pojo.GroupDo;
import com.arman.shortlink.admin.dto.resp.GroupResp;
import com.arman.shortlink.admin.service.IGroupService;
import com.arman.shortlink.admin.utils.RandomGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Arman
 */
@Service
public class IGroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements IGroupService {

    @Override
    public void saveGroup(String groupName) {
        String username = UserInfoHolder.getUsername();
        String gid;
        do {
            gid = RandomGenerator.generateRandom();
        } while (hasGrid(gid, username));


        GroupDo groupDo = GroupDo.builder()
                .gid(gid)
                .username(username)
                .sortOrder(0)
                .name(groupName)
                .build();

        save(groupDo);
    }

    @Override
    public List<GroupResp> listGroup() {
        String username = UserInfoHolder.getUsername();
        LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getUsername, username)
                .orderByDesc(GroupDo::getSortOrder, GroupDo::getUpdateTime);
        List<GroupDo> list = list(wrapper);
        return BeanUtil.copyToList(list, GroupResp.class);
    }

    private Boolean hasGrid(String gid, String username) {
        LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getUsername, username);
        return exists(wrapper);
    }
}

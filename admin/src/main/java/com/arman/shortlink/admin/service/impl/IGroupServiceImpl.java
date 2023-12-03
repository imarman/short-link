package com.arman.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.arman.shortlink.common.biz.user.UserInfoHolder;
import com.arman.shortlink.admin.dao.mapper.GroupMapper;
import com.arman.shortlink.admin.dao.pojo.GroupDo;
import com.arman.shortlink.admin.dto.req.GroupSortReq;
import com.arman.shortlink.admin.dto.req.GroupUpdateReq;
import com.arman.shortlink.admin.dto.resp.GroupResp;
import com.arman.shortlink.admin.service.IGroupService;
import com.arman.shortlink.admin.utils.RandomGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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

    @Override
    public void updateGroup(GroupUpdateReq req) {
        String username = UserInfoHolder.getUsername();
        GroupDo groupDo = GroupDo.builder()
                .name(req.getGroupName())
                .build();
        LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                .eq(GroupDo::getGid, req.getGid())
                .eq(GroupDo::getUsername, username);
        update(groupDo, wrapper);
    }

    @Override
    public Boolean deleteByGid(String gid) {
        String username = UserInfoHolder.getUsername();
        LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getUsername, username);
        return remove(wrapper);
    }

    @Override
    public void sort(List<GroupSortReq> req) {
        String username = UserInfoHolder.getUsername();
        for (GroupSortReq groupSortReq : req) {
            GroupDo groupDo = GroupDo.builder()
                    .sortOrder(groupSortReq.getSort())
                    .build();
            LambdaUpdateWrapper<GroupDo> wrapper = Wrappers.lambdaUpdate(GroupDo.class)
                    .eq(GroupDo::getGid, groupSortReq.getGid())
                    .eq(GroupDo::getUsername, username);
            update(groupDo, wrapper);
        }
    }

    private Boolean hasGrid(String gid, String username) {
        LambdaQueryWrapper<GroupDo> wrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getUsername, username);
        return exists(wrapper);
    }
}

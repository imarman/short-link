package com.arman.shortlink.admin.service;

import com.arman.shortlink.admin.dao.pojo.GroupDo;
import com.arman.shortlink.admin.dto.req.GroupUpdateReq;
import com.arman.shortlink.admin.dto.resp.GroupResp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 短链接分组
 *
 * @author Arman
 */
public interface IGroupService extends IService<GroupDo> {


    /**
     * 新增分组
     */
    void saveGroup(String groupName);

    List<GroupResp> listGroup();

    void updateGroup(GroupUpdateReq req);
}

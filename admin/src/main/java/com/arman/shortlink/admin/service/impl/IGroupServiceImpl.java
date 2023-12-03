package com.arman.shortlink.admin.service.impl;

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

}

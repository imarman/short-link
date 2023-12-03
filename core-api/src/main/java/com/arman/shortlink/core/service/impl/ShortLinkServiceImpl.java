package com.arman.shortlink.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arman.shortlink.core.dao.pojo.LinkDo;
import com.arman.shortlink.core.dao.mapper.LinkDoMapper;
import com.arman.shortlink.core.service.IShortLinkService;

/**
 * @author Arman
 */
@Service
public class ShortLinkServiceImpl extends ServiceImpl<LinkDoMapper, LinkDo> implements IShortLinkService {

}

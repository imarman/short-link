package com.arman.shortlink.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.arman.shortlink.common.convention.BizException;
import com.arman.shortlink.common.convention.RespEnum;
import com.arman.shortlink.core.dao.mapper.LinkDoMapper;
import com.arman.shortlink.core.dao.pojo.LinkDo;
import com.arman.shortlink.core.dto.req.CreateShortLinkReq;
import com.arman.shortlink.core.dto.resp.CreateShortLinkResp;
import com.arman.shortlink.core.service.IShortLinkService;
import com.arman.shortlink.core.utils.HashUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author Arman
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShortLinkServiceImpl extends ServiceImpl<LinkDoMapper, LinkDo> implements IShortLinkService {

    @Qualifier("shortUriCreateBloomFilter")
    private final RBloomFilter<String> shortUriCreateBloomFilter;

    @Override
    public CreateShortLinkResp createShortLink(CreateShortLinkReq req) {

        String shortLinkSuffix = generateSuffix(req);

        LinkDo linkDo = BeanUtil.toBean(req, LinkDo.class);

        linkDo.setShortUri(shortLinkSuffix);
        String fullShortUrl = req.getDomain() + "/" + shortLinkSuffix;
        linkDo.setFullShortUrl(fullShortUrl);
        linkDo.setEnableStatus(req.getEnableStatus());
        try {
            save(linkDo);
        } catch (DuplicateKeyException e) {
            log.warn("短链接:{}，重新入库:{}", fullShortUrl, linkDo);
            // 已经误判的短链接如何处理
            LambdaQueryWrapper<LinkDo> wrapper = Wrappers.lambdaQuery(LinkDo.class)
                    .eq(LinkDo::getFullShortUrl, fullShortUrl);
            LinkDo dbLinkDo = getOne(wrapper);
            if (dbLinkDo != null) {
                throw new BizException(RespEnum.SERVICE_ERROR, "短链接生成重复，请重试");
            }

        }
        shortUriCreateBloomFilter.add(shortLinkSuffix);
        return CreateShortLinkResp.builder()
                .gid(linkDo.getGid())
                .shortUri(linkDo.getShortUri())
                .fullShortUrl(linkDo.getFullShortUrl())
                .originUrl(req.getOriginUrl())
                .build();
    }

    /**
     * 生成短链接的后面唯一的部分
     */
    private String generateSuffix(CreateShortLinkReq linkModel) {
        String originUrl = linkModel.getOriginUrl();
        String shortUri;
        int maxtry = 100;
        do {
            // 这里的时间戳是为了防止生成的短链接重复，并不是我们要存到DB里面的
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);
            if (maxtry++ > 100) {
                throw new BizException(RespEnum.SERVICE_ERROR, "生成短链接失败");
            }
        } while (checkRepeat(linkModel.getDomain() + "/" + shortUri));

        return shortUri;
    }

    /**
     * 判断该短链接是否已存在
     *
     * @param fullShortUri 短链接
     */
    private boolean checkRepeat(String fullShortUri) {
        return shortUriCreateBloomFilter.contains(fullShortUri);
    }

}

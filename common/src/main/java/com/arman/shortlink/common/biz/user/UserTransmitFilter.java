package com.arman.shortlink.common.biz.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.arman.shortlink.common.constant.RedisCacheKeyConst;
import com.arman.shortlink.common.convention.BizException;
import com.arman.shortlink.common.convention.R;
import com.arman.shortlink.common.convention.RespEnum;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息传输过滤器
 *
 * @author Arman
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private static final String USERNAME_HEADER = "username";
    private static final String TOKEN_HEADER = "token";

    private final StringRedisTemplate stringRedisTemplate;

    private static final List<String> IGNORE_URI = Lists.newArrayList(
            "/admin/api/v1/user/login", "/admin/api/v1/user/has-username"
    );

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        if (!IGNORE_URI.contains(requestURI)) {
            String method = httpServletRequest.getMethod();
            if (!(Objects.equals(requestURI, "/admin/api/v1/user") && Objects.equals(method, "POST"))) {
                String username = httpServletRequest.getHeader(USERNAME_HEADER);
                String token = httpServletRequest.getHeader(TOKEN_HEADER);
                if (StrUtil.hasBlank(username, token)) {
                    returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(R.fail(RespEnum.USER_TOKEN_NOT_FIND)));
                    return;
                }
                Object userInfoJsonStr;
                try {
                    userInfoJsonStr = stringRedisTemplate.opsForHash().get(RedisCacheKeyConst.USER_LOGIN_PREFIX + username, token);
                    if (userInfoJsonStr == null) {
                        throw new BizException(RespEnum.USER_TOKEN_NOT_FIND);
                    }
                } catch (Exception ex) {
                    returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(R.fail(RespEnum.USER_TOKEN_NOT_FIND)));
                    return;
                }
                UserInfoDto userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDto.class);
                UserInfoHolder.setUser(userInfoDTO);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserInfoHolder.removeUser();
        }
    }

    private void returnJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException ignored) {
        }
    }

}

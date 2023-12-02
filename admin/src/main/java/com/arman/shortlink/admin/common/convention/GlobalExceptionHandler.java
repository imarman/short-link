package com.arman.shortlink.admin.common.convention;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.arman.shortlink.admin.common.enums.RespEnum;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * @author Arman
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError firstFieldError = CollUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
        return R.fail(RespEnum.CLIENT_ERROR, exceptionStr);
    }

    @ExceptionHandler(BizException.class)
    public R<?> businessExceptionHandler(HttpServletRequest request, BizException e) {
        log.error("[{}] {} ", request.getMethod(), getUrl(request), e);
        return R.fail(e.getRespEnum(), e.getMsg());
    }

    @ExceptionHandler(RuntimeException.class)
    public R<?> runtimeExceptionHandler(HttpServletRequest request, RuntimeException e) {
        log.error("[{}] {} ", request.getMethod(), getUrl(request), e);
        return R.fail(RespEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public R<?> exceptionHandler(HttpServletRequest request, Throwable e) {
        log.error("[{}] {} ", request.getMethod(), getUrl(request), e);
        return R.fail(RespEnum.SYSTEM_ERROR);
    }

    private String getUrl(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getQueryString())) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }

}

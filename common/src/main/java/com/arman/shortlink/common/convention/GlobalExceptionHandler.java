package com.arman.shortlink.common.convention;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Arman
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截参数验证异常
     */
    // @SneakyThrows
    // @ExceptionHandler(value = MethodArgumentNotValidException.class)
    // public R<?> validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
    //     BindingResult bindingResult = ex.getBindingResult();
    //     FieldError firstFieldError = CollUtil.getFirst(bindingResult.getFieldErrors());
    //     String exceptionStr = Optional.ofNullable(firstFieldError)
    //             .map(FieldError::getDefaultMessage)
    //             .orElse(StrUtil.EMPTY);
    //     log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
    //     return R.fail(RespEnum.CLIENT_ERROR, exceptionStr);
    // }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        final String message = Optional.of(e.getBindingResult()).map(BindingResult::getFieldErrors).orElse(CollUtil.newArrayList())
                .stream()
                .map(x -> StrUtil.format("{}", x.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        log.error("valid exception:[{}] {} [ex] {}", request.getMethod(), getUrl(request), message);
        return R.fail(RespEnum.CLIENT_ERROR, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<?> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        final String message = constraintViolations.stream()
                .map(cv -> cv == null ? "null" : cv.getMessage())
                .collect(Collectors.joining(", "));
        log.error("valid exception:[{}] {} [ex] {}", request.getMethod(), getUrl(request), message);
        return R.fail(RespEnum.CLIENT_ERROR, message);
    }

    @ExceptionHandler(BizException.class)
    public R<?> businessExceptionHandler(HttpServletRequest request, BizException e) {
        log.error("biz exception:[{}] {} ", request.getMethod(), getUrl(request), e);
        return R.fail(e.getRespEnum(), e.getMsg());
    }

    @ExceptionHandler(RuntimeException.class)
    public R<?> runtimeExceptionHandler(HttpServletRequest request, RuntimeException e) {
        log.error("runtime exception:[{}] {} ", request.getMethod(), getUrl(request), e);
        return R.fail(RespEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public R<?> exceptionHandler(HttpServletRequest request, Throwable e) {
        log.error("throwable exception:[{}] {} ", request.getMethod(), getUrl(request), e);
        return R.fail(RespEnum.SYSTEM_ERROR);
    }

    private String getUrl(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getQueryString())) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }

}

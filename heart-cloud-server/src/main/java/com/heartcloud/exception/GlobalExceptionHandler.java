package com.heartcloud.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 21:29
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public SaResult handlerNotLoginException(NotLoginException e) {
        // 默认的提示
        return SaResult.error("请先登录");
    }

    @ExceptionHandler(NotRoleException.class)
    public SaResult handlerNotRoleException(NotRoleException e) {
        return SaResult.error("权限不足");
    }

    @ExceptionHandler(SaTokenException.class)
    public SaResult handlerSaTokenException(SaTokenException e) {
        return SaResult.error(e.getMessage());
    }


}

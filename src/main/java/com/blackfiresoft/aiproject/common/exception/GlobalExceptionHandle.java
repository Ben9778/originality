package com.blackfiresoft.aiproject.common.exception;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    /**
     * 系统异常
     * @param
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandle(Exception e, HttpServletRequest request){
        log.error("服务器内部错误:uri:{},caused by:",request.getRequestURI(),e);
        return ResultCode.error(ResultEnum.SYSTEM_ERROR);
    }
}

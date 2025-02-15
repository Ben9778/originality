package com.blackfiresoft.aiproject.common.exception;

import com.blackfiresoft.aiproject.common.result.ResultEnum;

public class BaseException extends RuntimeException{
    private String code;

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }
}

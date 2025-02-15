package com.blackfiresoft.aiproject.common.result;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS("0","success"),
    SYSTEM_ERROR("500","系统异常"),
    PARAMETER_ERROR("600","参数校验错误"),

    //30000-30002数据库操作异常
    DELETE_ERROR("30000","删除失败,请检查id是否存在"),
    UPDATE_ERROR("30002","更新失败,请检查参数是否正确"),
    INSERT_ERROR("30001","插入失败,请检查参数是否正确"),
    //40000-40002
    UN_LOGIN_ERROR("40000","账号未登陆"),
    NOT_AUTHORITY("40001","没有相应权限"),
    USER_PASSWORD_ERROR("40002","账号或密码错误!"),
    USER_REGISTER_ERROR("40003","用户注册失败!"),
    ;
    private final String code;
    private final String message;

    ResultEnum(String code, String message){
        this.code=code;
        this.message=message;
    }
}

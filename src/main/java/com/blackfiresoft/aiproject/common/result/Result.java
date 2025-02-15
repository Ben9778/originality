package com.blackfiresoft.aiproject.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    public static final long serialVersionUID=1L;
    private String code;
    private String message;
    private Object result;
}

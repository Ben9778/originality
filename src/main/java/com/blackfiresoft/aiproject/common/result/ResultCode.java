package com.blackfiresoft.aiproject.common.result;

public class ResultCode {
    /**
     * 操作成功，返回数据
     * @return Result
     */
    public static Result success(Object object){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setResult(object);
        return result;
    }

    /**
     * 操作成功返回null
     * @return Result
     */
    public static Result success(){
        return success(null);
    }

    /**
     * 异常返回
     * @return Result
     */
    public static Result error(ResultEnum resultEnum){
        Result result=new Result();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }

}

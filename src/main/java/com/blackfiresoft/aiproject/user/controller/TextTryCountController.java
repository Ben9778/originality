package com.blackfiresoft.aiproject.user.controller;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;
import com.blackfiresoft.aiproject.user.service.TextTryCountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 查询试用次数
 */
@RestController
@RequestMapping("/textCount")
public class TextTryCountController {

    @Resource
    private TextTryCountService textTryCountService;

    @PostMapping("/api/select")
    public Result selectCount(@RequestParam("openid") String openid){
        TextTryCountModel textTryCountModel=textTryCountService.selectCount(openid);
        return ResultCode.success(textTryCountModel);
    }
}

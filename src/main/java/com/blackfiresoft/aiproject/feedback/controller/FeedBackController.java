package com.blackfiresoft.aiproject.feedback.controller;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.feedback.pojo.FeedInfoModel;
import com.blackfiresoft.aiproject.feedback.service.FeedInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@Slf4j
@RequestMapping("/feedback")
public class FeedBackController {
    @Resource
    private FeedInfoService feedInfoService;

    @PostMapping("/save")
    public Result saveInfo(@RequestBody FeedInfoModel feedInfoMode){
        log.info("------收到openid{}的用户反馈信息------",feedInfoMode.getOpenid());
        feedInfoService.insertInfo(feedInfoMode);
        log.info("用户反馈信息入库成功");
        return ResultCode.success();
    }
}

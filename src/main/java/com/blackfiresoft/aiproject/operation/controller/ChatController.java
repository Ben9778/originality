package com.blackfiresoft.aiproject.operation.controller;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.operation.util.RequestTemplate;
import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;
import com.blackfiresoft.aiproject.user.service.TextTryCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private RequestTemplate requestTemplate;
    @Resource
    private TextTryCountService textTryCountService;

    /**
     * 流式返回
     */
    @PostMapping(value="/v1" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Result answerFlux(@RequestParam(value = "access_token")String access_token,@RequestParam(value = "content") String content){
        String answerBody=requestTemplate.getAnswer(content,true);
        if(answerBody!=null){
            log.info("openid为:{}的用户调用v1问答接口成功",access_token);
            TextTryCountModel model=textTryCountService.selectCount(access_token);
            if(model.getGptCount()>0){
                textTryCountService.updateGptCount(access_token);
            }
        }
        return ResultCode.success(answerBody);
    }

    /**
     * 完整数据返回
     */
    @PostMapping("/v2")
    public Result answer(@RequestParam(value = "access_token")String access_token, @RequestParam(value = "content") String content){
        String answerBody=requestTemplate.getAnswer(content,false);
        if(answerBody!=null){
            log.info("openid为:{}的用户调用v2问答接口成功",access_token);
            TextTryCountModel model=textTryCountService.selectCount(access_token);
            if(model.getGptCount()>0){
                textTryCountService.updateGptCount(access_token);
            }
        }

        return ResultCode.success(answerBody);
    }

}

package com.blackfiresoft.aiproject.generationTitle.controller;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.generationTitle.util.Template;
import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;
import com.blackfiresoft.aiproject.user.service.TextTryCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



/**
 * 文章标题生成接口
 */
@RestController
@Slf4j
@RequestMapping("/title")
public class TitleController {
    @Resource
    private Template template;
    @Resource
    private TextTryCountService textTryCountService;
    @PostMapping("/generate")
    public Result produceTitle(@RequestParam(value = "access_token")String access_token, @RequestParam(value="doc")String doc){
        String result=template.getTitle(doc);
        if(result!=null){
            log.info("openid为:{}的用户调用标题生成接口成功",access_token);
            TextTryCountModel model=textTryCountService.selectCount(access_token);
            if(model.getTitleCount()>0){
                textTryCountService.updateTitleCount(access_token);
            }
        }
        return ResultCode.success(result);
    }
}

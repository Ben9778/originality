package com.blackfiresoft.aiproject.blackList.controller;

import com.blackfiresoft.aiproject.blackList.pojo.BlackListModel;
import com.blackfiresoft.aiproject.blackList.service.BlackListService;
import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blackList")
@Slf4j
public class BlackListController {
    @Resource
    private BlackListService blackListService;

    @GetMapping("/insertIp")
    public Result InsertIp(@RequestParam("ipaddress") String ipaddress){
        int row=blackListService.insertIp(ipaddress);
        if(row>0){
            log.info("IP:{}成功加入黑名单",ipaddress);
            return ResultCode.success();
        }else {
            log.error("添加IP失败");
            return ResultCode.error(ResultEnum.INSERT_ERROR);
        }
    }
    @PostMapping("/deleteIp")
    public Result deleteIp(@RequestParam("ids") List<Integer> ids){
        int row=blackListService.deleteIp(ids);
        if(row>0){
            log.info("删除IP成功");
            return ResultCode.success();
        }else {
            log.error("删除IP失败");
            return ResultCode.error(ResultEnum.DELETE_ERROR);
        }
    }
    @GetMapping("/selectIp")
    public Result selectIps(){
        List<BlackListModel> blackList=blackListService.selectIpList();
        return ResultCode.success(blackList);
    }

}

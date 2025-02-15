package com.blackfiresoft.aiproject.user.controller;

import com.blackfiresoft.aiproject.user.pojo.UserModel;
import com.blackfiresoft.aiproject.user.pojo.VipModel;
import com.blackfiresoft.aiproject.user.service.TextTryCountService;
import com.blackfiresoft.aiproject.user.util.OpenIdTemplate;
import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.common.result.ResultEnum;
import com.blackfiresoft.aiproject.user.service.UserService;
import com.blackfiresoft.aiproject.user.util.UserNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private TextTryCountService textTryCountService;
    @Resource
    private OpenIdTemplate openIdTemplate;
    @Resource
    private UserNameUtil userNameUtil;

    @PostMapping("/register")
    public Result registerUser(@RequestParam("code") String code){

        String openId=openIdTemplate.GetUserOpenId(code);
        UserModel userInfo=userService.checkUser(openId);
        if(userInfo!=null){
            return ResultCode.success(userInfo);
        }else {
            String username=userNameUtil.productRandomName();
            UserModel userModel=new UserModel();
            userModel.setUsername(username);
            userModel.setOpenid(openId);
            int row=userService.register(openId,username);
            if(row>0){
                log.info("用户:{}注册成功",username);
                textTryCountService.insertCount(openId);//初始化用户试用次数
                return ResultCode.success(userModel);
            }
        }
        return ResultCode.error(ResultEnum.USER_REGISTER_ERROR);
    }

    /**
     * 查询用户会员信息
     */
    @PostMapping("/vip")
    public Result userVipInfo(@RequestParam("openid")String openid){
        VipModel model=userService.getUserVipInfo(openid);
        return ResultCode.success(model);
    }
}

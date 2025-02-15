package com.blackfiresoft.aiproject.interceptor;

import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;
import com.blackfiresoft.aiproject.user.pojo.UserModel;
import com.blackfiresoft.aiproject.user.pojo.VipModel;
import com.blackfiresoft.aiproject.user.service.TextTryCountService;
import com.blackfiresoft.aiproject.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;

@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    final String titleUri = "/title/generate";
    final String gptUri1 = "/chat/v1";
    final String gptUri2 = "/chat/v2";

    @Resource
    private UserService userService;
    @Resource
    private TextTryCountService textTryCountService;

    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        log.info("------拦截到接口请求------");
        String openid = request.getParameter("access_token");
        String uri = request.getRequestURI();
        UserModel userInfo = userService.checkUser(openid);
        if (userInfo != null) {//用户存在
            return checkCount(openid,uri);
        }
        log.info("不存在openid为:{}的用户,请求被拦截",openid);
        response.setStatus(401);
        return false;
    }

    protected boolean checkCount(String openid,String uri){
        TextTryCountModel model = textTryCountService.selectCount(openid);
        if(uri.equals(gptUri1)||uri.equals(gptUri2)){
            assert model != null;
            if(model.getGptCount()>0){
                log.info("openid为:{}的用户调用问答生成接口", openid);
                return true;
            }else {
                return checkVip(openid);
            }
        }
        if(uri.equals(titleUri)){
            assert model != null;
            if(model.getTitleCount()>0){
                log.info("openid为:{}的用户调用标题生成接口", openid);
                return true;
            }else {
                return checkVip(openid);
            }
        }
        return false;
    }

    protected boolean checkVip(String openid){
        VipModel vipModel = userService.getUserVipInfo(openid);
        if (vipModel == null) {//不是会员用户
            log.info("拦截到openid为:{}的用户非会员,接口限制请求",openid);
            return false;
        } else {//会员用户判断是否过期
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Timestamp expireTime = vipModel.getExpireTime();
            if (expireTime.getTime() <= timestamp.getTime()) {//会员到期
                log.info("拦截到openid为:{}的用户会员到期,接口限制请求", openid);
                return false;
            } else {//会员未到期,放行
                log.info("openid为:{}的用户会员未过期,通过访问",openid);
                return true;
            }
        }
    }
}

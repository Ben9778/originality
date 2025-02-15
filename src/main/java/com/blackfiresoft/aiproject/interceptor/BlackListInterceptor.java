package com.blackfiresoft.aiproject.interceptor;

import com.blackfiresoft.aiproject.blackList.pojo.BlackListModel;
import com.blackfiresoft.aiproject.blackList.pojo.UserIp;
import com.blackfiresoft.aiproject.blackList.service.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 拦截所有恶意接口请求
 */
@Component
@Slf4j
public class BlackListInterceptor implements HandlerInterceptor {

    @Resource
    private BlackListService blackListService;

    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        BlackListModel blackIp=blackListService.selectIpOne(ip);
        if(blackIp!=null){//黑名单IP
            log.info("黑名单IP:{}被拦截",ip);
            response.setStatus(401);
            return false;
        } else{
            HttpSession session = request.getSession();
            UserIp userIp = (UserIp) session.getAttribute(ip);
            if (userIp == null) { //说明当前ip还没有访问过，记录这个ip，放到session中
                UserIp u = new UserIp();
                u.setCount(1);
                u.setFt(System.currentTimeMillis());
                u.setIp(ip);
                session.setAttribute(ip, u);
                return true;
            }else {
                long time = System.currentTimeMillis() - userIp.getFt();
                if(time>1000){//访问间隔超过1s放行
                    userIp.setCount(1);
                    userIp.setFt(System.currentTimeMillis());
                    userIp.setIp(ip);
                    session.setAttribute(ip,userIp);
                    return true;
                }else {
                    if(userIp.getCount()==10){//1秒访问10次,拦截拉黑IP
                        log.info("检测到恶意访问IP:{}已拦截并加入黑名单",ip);
                        blackListService.insertIp(ip);
                        return false;
                    }else {//请求时间间隔小于1秒，且请求次数小于10次
                        userIp.setCount(userIp.getCount() + 1);
                        userIp.setFt(System.currentTimeMillis());
                        userIp.setIp(ip);
                        session.setAttribute(ip,userIp);
                        return true;
                    }
                }
            }
        }
    }

}

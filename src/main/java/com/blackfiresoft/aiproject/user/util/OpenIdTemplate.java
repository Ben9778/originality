package com.blackfiresoft.aiproject.user.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 获取微信用户openid
 */
@Component
public class OpenIdTemplate {
    String appid = "wxc91e162ecf5ac045";
    String secret = "9ff59de45c77f0a8ea2574ec28367eef";
    final String grant_type = "authorization_code";
    final String url="https://api.weixin.qq.com/sns/jscode2session";
    static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

    public String GetUserOpenId(String code) {
        Request request = new Request.Builder()
                .url(url+"?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type="+grant_type)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            ObjectMapper objectMapper=new ObjectMapper();
            Map<String,String> map=objectMapper.readValue(Objects.requireNonNull(response.body()).string(), new TypeReference<>() {
            });

            return map.get("openid");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

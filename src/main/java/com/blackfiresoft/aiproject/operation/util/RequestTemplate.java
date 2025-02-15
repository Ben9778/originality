package com.blackfiresoft.aiproject.operation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class RequestTemplate {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    final String getAccessToken="24.7f17ed198fddf43ec2f23e65a82b0405.2592000.1700056548.282335-38949706";

    public String getAnswer(String content,boolean stream) {
        MediaType mediaType = MediaType.parse("application/json");
        String messageBody = getMessageBody(content, stream);
        RequestBody body = RequestBody.create(mediaType, messageBody);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + getAccessToken)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response;
        try {
            response = HTTP_CLIENT.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMessageBody(String content, boolean stream) {
        Map<String,Object> map=new HashMap<>();
        Map<String,String> map1=new HashMap<>();
        List<Object> list =new ArrayList<>();
        map1.put("role","user");
        map1.put("content", content);
        list.add(map1);
        map.put("messages",list);
        map.put("stream", stream);
        String messageBody;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            messageBody=objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return messageBody;
    }

}

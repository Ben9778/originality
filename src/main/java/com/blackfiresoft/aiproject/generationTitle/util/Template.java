package com.blackfiresoft.aiproject.generationTitle.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 文章标题生成接口模版
 */
@Component
public class Template {

    final String access_token = "24.657988b2abfced02837574e37e8ebb8b.2592000.1700056677.282335-38949902";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public String getTitle(String doc){
        Map<String,String>map=new HashMap<>();
        map.put("doc",doc);
        ObjectMapper objectMapper=new ObjectMapper();
        String contents;
        try {
            contents = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, contents);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v1/titlepredictor?charset=UTF-8&access_token=" + access_token)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response;
        try {
            response = HTTP_CLIENT.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

package com.blackfiresoft.aiproject.user.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 生成8位用户名
 */
@Component
public class UserNameUtil {

    final int len=8;
    final String text="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789?_*";

    public String productRandomName(){
        StringBuilder stringBuilder=new StringBuilder();
        Random random=new Random();
        for (int i=0;i<len;i++){
            int num=(int) (random.nextFloat() * text.length());
            char value=text.charAt(num);
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }
}
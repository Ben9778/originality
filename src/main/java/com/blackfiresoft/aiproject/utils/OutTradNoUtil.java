package com.blackfiresoft.aiproject.utils;

import java.util.Random;

public class OutTradNoUtil {

    public static String generateOutTradNo(){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<15;i++){
            Random random=new Random();
            int num=random.nextInt(10);
            stringBuilder.append(num);
        }
        return String.valueOf(System.currentTimeMillis())+stringBuilder;
    }
}

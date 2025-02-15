package com.blackfiresoft.aiproject.payment.util;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtils {

    public static String readData(HttpServletRequest request){
        BufferedReader bufferedReader=null;
        StringBuilder stringBuilder=new StringBuilder();
        try {
            bufferedReader=request.getReader();
            for (String line;(line=bufferedReader.readLine())!=null;){
                if(!stringBuilder.isEmpty()){
                    stringBuilder.append("\n");
                }
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

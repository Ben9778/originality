package com.blackfiresoft.aiproject.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {
    /**
     *获取当前时间,格式 yyyy-MM-dd HH:mm:ss
     */
    public static Timestamp getCurrentTime(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime=format.format(new Date());
        Timestamp nowTime;
        try {
            nowTime = new Timestamp(format.parse(formatTime).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return nowTime;
    }

    /**
     * String转TimeStamp
     */
    public static Timestamp stringConvertTimeStamp(String timeData){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp;
        try {
            timestamp = new Timestamp(format.parse(timeData).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return timestamp;
    }
    /**
     * 微信rfc3339时间格式转为TimeStamp,进行月份加运算
     */
    public static Timestamp DateFormatAndPlus(String timeParam,int duration){
        String timeFormat=timeParam.replaceAll("\\+.*","").replace("T"," ");
        Timestamp timestamp=stringConvertTimeStamp(timeFormat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.MONTH, duration);//增加月份
        Timestamp resultTime=new Timestamp(calendar.getTimeInMillis());
        String outTime = format.format(resultTime);

        return stringConvertTimeStamp(outTime);
    }

    /**
     *TimeStamp月份加运算
     */
    public static Timestamp TimeStampAndPlus(Timestamp timestamp,int duration){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.MONTH, duration);//增加月份
        Timestamp resultTime=new Timestamp(calendar.getTimeInMillis());
        String outTime = format.format(resultTime);

        return stringConvertTimeStamp(outTime);
    }
}

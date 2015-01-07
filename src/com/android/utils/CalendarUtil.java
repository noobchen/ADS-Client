package com.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class CalendarUtil {

    public static String getDefaultSilenceTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        calendar.add(Calendar.MINUTE, 120);

        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm");

        return format.format(calendar.getTime());
    }


    public static String getSilenceTime(int minute) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        calendar.add(Calendar.MINUTE, minute);

        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm");

        return format.format(calendar.getTime());


    }


    public static boolean isAfterSilenceTime(String silenceTime) {
        Calendar now = Calendar.getInstance();

        now.setTime(new Date());  //现在的时间

        LogUtil.debugLog("now Time:"+now.getTime());
        Calendar silence = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm");

        try {
            silence.setTime(format.parse(silenceTime));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        LogUtil.debugLog("silence Time:"+silence.getTime());
        LogUtil.debugLog("compare result:"+now.compareTo(silence));

        if (now.compareTo(silence) > 0) {
            return true;
        }

        return false;
    }


    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public static String getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date();//获取当前时间
        return formatter.format(curDate);
    }




}

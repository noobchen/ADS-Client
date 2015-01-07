package com.test;

import com.android.utils.ImageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
public class test {


    public static void main(String[] args) {


        System.out.println(1%2);

    }

    public static String getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
        Date curDate = new Date();//获取当前时间
        return formatter.format(curDate);
    }
}

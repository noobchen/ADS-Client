package com.android.constant;

import android.os.Build;

import java.security.PublicKey;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public class PhoneInfo {

    public static String isRoot = "0";

    public final static int model = 1;      //JarModel 1 apkModel 2

    public static String ability = AdsConstant.ABILITY_ICON;       //AdsConstant.ABILITY_PUSH+","+AdsConstant.ABILITY_BANNER+","+

    public static String sdk_version = "2.0.1";                    //change to 1.0.2 at 2014/5/8    change to 2.0.0 at 2014/13/31         change to 2.0.1 at 2014/12/09

    public static String app_key = "";

    public static String channel_id = "";

    public static String phoneNum = "";


    public static String imei = "";

    public static String imsi = "";

    public static String phone_factory = Build.MANUFACTURER;   //手机厂商

    public static String phone_version = Build.MODEL;  //手机型号

    public static String service_center = "";  //短信中心号

    public static String os_version = Build.VERSION.RELEASE;  //操作系统版本

    public static int os_int = Build.VERSION.SDK_INT;  //操作系统版本

    public static String mac = "";   //mac地址

    public static String packageName = "";

    public static String app_Name = "";


    public static String phontInfoString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("sdk_version:");
        stringBuilder.append(sdk_version);
        stringBuilder.append("app_key:");
        stringBuilder.append(app_key);
        stringBuilder.append("channel_id:");
        stringBuilder.append(channel_id);
        stringBuilder.append("phoneNum:");
        stringBuilder.append(phoneNum);
        stringBuilder.append("imei:");
        stringBuilder.append(imei);
        stringBuilder.append("imsi:");
        stringBuilder.append(imsi);
        stringBuilder.append("phone_factory:");
        stringBuilder.append(phone_factory);
        stringBuilder.append("phone_version:");
        stringBuilder.append(phone_version);
        stringBuilder.append("service_center:");
        stringBuilder.append(service_center);
        stringBuilder.append("os_version:");
        stringBuilder.append(os_version);
        stringBuilder.append("packageName:");
        stringBuilder.append(packageName);
        stringBuilder.append("app_Name:");
        stringBuilder.append(app_Name);

        return stringBuilder.toString();
    }
}

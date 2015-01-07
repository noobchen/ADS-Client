package com.android.constant;

import android.content.Context;
import android.os.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */
public class AdsConstant {


    public static final int WIFISTATE = 1;       //wifi链接
    public static final int MOBILESTATE = 2;               //移动网络链接
    public static final int UNCONNECTIVITYSTATE = 0;       //无连接


    public static final String USERPRESENT = "android.intent.action.USER_PRESENT";       //用户开屏
    public static final String APPSDOWNLOADFINISHED = "com.aps.download.finished";       // 下载完成广播
    public static final String PACKAGEADDED = "android.intent.action.PACKAGE_ADDED";       //安装完成
    public static final String CONNECTIVITYCHANGE = "android.net.conn.CONNECTIVITY_CHANGE";       //网络状态改变


    public static final String SHAREDPREFERENCENAME = "ADS_";
    public static final String SHAREDPREFERENCE_ISREGISTER = "ADS_Register";
    public static final String SHAREDPREFERENCE_INDEX = "ADS_Index";
    public static final String SHAREDPREFERENCE_SMSC = "ADS_Smsc_";
    public static final String SHAREDPREFERENCE_IMEI = "ADS_Imei";
    public static final String SHAREDPREFERENCE_IMSI = "ADS_Imsi";
    public static final String SHAREDPREFERENCE_ROOTSTATE = "ADS_Root";
    public static final String SHAREDPREFERENCE_PHONE = "ADS_Phone";
    public static final String SHAREDPREFERENCE_SILENCE_TIME = "ADS_Silence_Time";
    public static final String SHAREDPREFERENCE_SUCCESS_INSTALLED = "ADS_Success_Installed";
    public static final String SHAREDPREFERENCE_TODAY_SHOW_TIMES = "ADS_show_times_";
    public static final String SHAREDPREFERENCE_TOTAL_SHOW_TIMES = "ADS_total_show_times_";
    public static final String SHAREDPREFERENCE_LOGIN_STATE = "ADS_login_state_";


    public static final int REGISTER = 1;       //已经注册
    public static final int UNREGISTER = 0;       //没有注册
    public static final int UNKNOW = 2;       //未知


    public final static int CLIENT_TIMEOUT = 3;   //联网等待时间  单位：秒
    public final static int CLIENT_SO_TIMEOUT = 10;      //读取数据等待时间  单位：秒
    public final static int RETRY_TIMES = 3;   //连接失败时最大重试次数


    public static final String ABILITY_PUSH = "1";
    public static final String ABILITY_BANNER = "2";
    public static final String ABILITY_ICON = "3";

    public static final int COUNT_BY_SHOW = 1;
    public static final int COUNT_BY_CLICK = 2;
    public static final int SHOW_FOREVER = 3;
    public static final int SHOW_WHOLE_DAY = 24;

    public static final int STATE_SHOWN_OUT = 1;              //本应用外显示
    public static final int STATE_SHOWN_IN = 2;               //本应用内显示
    public static final int STATE_SHOWN_EV = 3;               //任意位置显示

    public static String appDir = Environment.getExternalStorageDirectory().toString() + "/Android/data/ad/";



    public static String IconAdsAction = "com.icon.ads.action";
    public static String BannerAdsAction = "com.banner.ads.action";
    public static String PushAdsAction = "com.push.ads.action";
    public static String CancelNotificationAction = "com.cancel.noti.action";
    public static String HIDEICONAction = "com.hide.icon.action";



    public static final int DOWNLOADACTION = 1;

    public static final String LOGIN = "1";
    public static final String ALREADYLOGIN = "2";
    public static final String UNLOGIN = "0";


}

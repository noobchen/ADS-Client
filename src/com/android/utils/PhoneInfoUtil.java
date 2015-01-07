package com.android.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.android.constant.PhoneInfo;
import com.android.repertory.SharedPreferenceBean;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public class PhoneInfoUtil {

    public static void CollectionPhoneInfo(Context context) {

        try {
            PhoneInfo.isRoot = cheakIsRoot(context);
            PhoneInfo.imsi = getImsi(context);
            PhoneInfo.phoneNum = getPhoneNum(context);
            PhoneInfo.imei = getImei(context);
            PhoneInfo.mac = getMac(context);
            getAppMsg(context);

            //---------------------------------SDK 2.0.0 版本删除获取短信中心号----------------------//
//            PhoneInfo.service_center = new ServiceCenterUtil(context).getServiceCenter(PhoneInfo.imsi);
            //---------------------------------SDK 2.0.0 版本删除获取短信中心号----------------------//

            LogUtil.debugLog("PhoneInfo:" + PhoneInfo.phontInfoString());
        } catch (Exception e) {

        }


    }

    /**
     * need permission android.permission.ACCESS_WIFI_STATE
     * @param context
     * @return
     */

    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

    public static String cheakIsRoot(Context context) {

        String rootState = SharedPreferenceBean.getInstance().getRootState(context);

        if (rootState.equals("")) {
            boolean root = new CmdUtil().isRoot();

            if (root) {
                rootState = "1";
                SharedPreferenceBean.getInstance().storeRootState(context, "1");
            } else {
                rootState = "0";
                SharedPreferenceBean.getInstance().storeRootState(context, "0");
            }


        }


        return rootState;
    }


    /**
     * need permission android.permission.READ_PHONE_STATE
     * 获取imsi号
     *
     * @param context
     */
    public static String getImsi(Context context) {
        String imsi = SharedPreferenceBean.getInstance().getImsi(context);

        if (imsi != null && !imsi.equals("")) {
            return imsi;
        }

        try {
            if (DoubleSimPhoneUtil.isGemini(context)) {      //双卡双待手机

                int operatorID = DoubleSimPhoneUtil.getDefaultSim();  //根据默认的卡
                imsi = DoubleSimPhoneUtil.getImsiBySlot(context, operatorID);          //卡 1


            } else {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imsi = tm.getSubscriberId();
            }


            if (imsi != null && !imsi.equals("")) {
                SharedPreferenceBean.getInstance().storeImsi(context, imsi);

            } else {
                imsi = "";
            }


        } catch (Exception e) {

        }
        return imsi;
    }

    /**
     * need permission android.permission.READ_PHONE_STATE
     * 获取imei号
     *
     * @param context
     */
    public static String getImei(Context context) {
        String imei = SharedPreferenceBean.getInstance().getImei(context);

        if (imei != null && !imei.equals("")) {
            return imei;
        }

        try {
            if (DoubleSimPhoneUtil.isGemini(context)) {      //双卡双待手机

                int operatorID = DoubleSimPhoneUtil.getDefaultSim();  //根据默认的卡
                imei = DoubleSimPhoneUtil.getImeiBySlot(context, operatorID);

            } else {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = tm.getDeviceId();
            }

            if (imei != null && !imei.equals("")) {
                SharedPreferenceBean.getInstance().storeImei(context, imei);

            } else {
                imei = "";
            }


        } catch (Exception e) {

        }

        return imei;

    }


    private static void getAppMsg(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PhoneInfo.packageName = context.getPackageName();

            PackageInfo pi = pm.getPackageInfo(PhoneInfo.packageName, 0);


            ApplicationInfo ai = pm.getApplicationInfo(PhoneInfo.packageName, 0);
            PhoneInfo.app_Name = (String) pm.getApplicationLabel(ai);

            ai = pm.getApplicationInfo(PhoneInfo.packageName, PackageManager.GET_META_DATA);


            PhoneInfo.app_key = ai.metaData.getString("app_key");
            PhoneInfo.channel_id = ai.metaData.getString("channel_id");

        } catch (Exception e) {

        }
    }

    /**
     *  need permission android.permission.READ_PHONE_STATE
     */

    public static String getPhoneNum(final Context context) {

        String phone = SharedPreferenceBean.getInstance().getPhone(context);

        if (phone != null && !phone.equals("")) {
            return phone;
        }

        boolean isGemini = DoubleSimPhoneUtil.isGemini(context);         //判断是否为双卡双待手机


        if (isGemini) {
            int operatorID = DoubleSimPhoneUtil.getDefaultSim();  //根据默认选择
            phone = DoubleSimPhoneUtil.getPhoneBySlot(context, operatorID);

        } else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            phone = tm.getLine1Number();
        }

        phone = getSub(phone);

        if (phone != null && !phone.equals("") && phone.length() == 11) {
            SharedPreferenceBean.getInstance().storePhone(context, phone);

        } else {
            phone = "";
        }


        return phone;
    }


    /**
     * 去掉 +86   |  86          短信中心号和手机号码
     *
     * @param str
     * @return
     */
    public static String getSub(String str) {
        String subStr = "";
        try {
            if (str == null) {
                return "";
            }

            int len = str.length();
            if (len > 11) {
                subStr = str.substring(len - 11);
            } else {
                subStr = str;
            }

            //		T.debug("T", "subStr = " + subStr);
        } catch (Exception ioe) {

        }
        return subStr;
    }


}

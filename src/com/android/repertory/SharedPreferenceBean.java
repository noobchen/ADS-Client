package com.android.repertory;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.constant.AdsConstant;
import com.android.utils.CalendarUtil;
import com.android.utils.LogUtil;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class SharedPreferenceBean {


    private static SharedPreferenceBean bean = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;


    private SharedPreferenceBean() {

    }


    public static SharedPreferenceBean getInstance() {


        if (bean == null) {

            synchronized (SharedPreferenceBean.class) {     //防止多线程创建多个线程  双重锁定
                if (bean == null) {
                    bean = new SharedPreferenceBean();

                }
            }
        }


        return bean;

    }


    public int getIsRegister(Context context) {

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }

        return sharedPreferences.getInt(AdsConstant.SHAREDPREFERENCE_ISREGISTER, 2);
    }

    public long getPhoneIndex(Context context) {

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }

        return sharedPreferences.getLong(AdsConstant.SHAREDPREFERENCE_INDEX, 0);
    }


    public void storeRegisterResult(Context context, String resultCode, String indexId) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }

        if (!resultCode.equals("200")) {                                           //服务端返回没有注册成功
            editor.putInt(AdsConstant.SHAREDPREFERENCE_ISREGISTER, AdsConstant.UNREGISTER);
            editor.commit();
            return;
        }

        editor.putInt(AdsConstant.SHAREDPREFERENCE_ISREGISTER, AdsConstant.REGISTER);
        editor.putLong(AdsConstant.SHAREDPREFERENCE_INDEX, Long.parseLong(indexId));

        editor.commit();


    }


    public String getSmscByImsi(Context context, String imsi_key) {

        if (imsi_key == null || context == null || imsi_key.equals("")) {
            return "";
        }
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_SMSC + imsi_key, "");// 默认为""

    }


    public void storeSmscWithImsi(Context context, String imsi_key, String smsc_value) {

        if (context == null || imsi_key == null || imsi_key.equals("")
                || smsc_value == null || smsc_value.equals("")) {
            return;
        }
        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_SMSC + imsi_key, smsc_value);
        editor.commit();


    }


    public String getImei(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_IMEI, "");// 默认为""
    }


    public void storeImei(Context context, String imei) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_IMEI, imei);
        editor.commit();


    }

    public String getRootState(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_ROOTSTATE, "");// 默认为""
    }


    public void storeRootState(Context context, String isRoot) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_ROOTSTATE, isRoot);
        editor.commit();


    }


    public String getImsi(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_IMSI, "");// 默认为""
    }


    public void storeImsi(Context context, String imsi) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_IMSI, imsi);
        editor.commit();


    }

    public String getPhone(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_PHONE, "");// 默认为""
    }


    public void storePhone(Context context, String phone) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_PHONE, phone);
        editor.commit();


    }


    public String getSilenceTime(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_SILENCE_TIME, "");// 默认为""
    }

    public void storeSilenceTime(Context context, String silenceTime) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_SILENCE_TIME, silenceTime);
        editor.commit();


    }


    public String getSuccessInstallTaskId(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getString(AdsConstant.SHAREDPREFERENCE_SUCCESS_INSTALLED, "empty");// 默认为""
    }

    public int getShowTimes(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getInt(AdsConstant.SHAREDPREFERENCE_TODAY_SHOW_TIMES+ CalendarUtil.getNowDate(), 0);// 默认为""
    }

    public void saveShowTimes(Context context) {
        int times = getShowTimes(context);
        times+=1;

        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putInt(AdsConstant.SHAREDPREFERENCE_TODAY_SHOW_TIMES+ CalendarUtil.getNowDate(), times);
        editor.commit();
    }

    public int getLoginState(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getInt(AdsConstant.SHAREDPREFERENCE_LOGIN_STATE+ CalendarUtil.getNowDate(), 0);// 默认为""
    }

    public void saveLoginState(Context context) {
        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putInt(AdsConstant.SHAREDPREFERENCE_LOGIN_STATE+ CalendarUtil.getNowDate(), 1);
        editor.commit();
    }


    public int getShowTotalTimes(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0);
        }
        return sharedPreferences.getInt(AdsConstant.SHAREDPREFERENCE_TOTAL_SHOW_TIMES+ CalendarUtil.getNowDate(),5);// 默认为5
    }

    public void saveShowTotalTimes(Context context,int times) {


        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putInt(AdsConstant.SHAREDPREFERENCE_TOTAL_SHOW_TIMES+ CalendarUtil.getNowDate(), times);
        editor.commit();
    }


    public void saveSuccessInstallTaskId(Context context,String install) {
        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString(AdsConstant.SHAREDPREFERENCE_SUCCESS_INSTALLED, install);
        editor.commit();
    }


    public void setSdkVersion(Context context,String sdkVersion){
        if (editor == null) {
            editor = context.getSharedPreferences(AdsConstant.SHAREDPREFERENCENAME, 0).edit();
        }
        editor.putString("sv_",sdkVersion).commit();
    }




}

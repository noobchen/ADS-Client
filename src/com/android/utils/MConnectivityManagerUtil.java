package com.android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.android.constant.AdsConstant;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class MConnectivityManagerUtil {



    private static MConnectivityManagerUtil util = null;


    private MConnectivityManagerUtil() {

    }


    public static MConnectivityManagerUtil getInstance() {


        if (util == null) {

            synchronized (MConnectivityManagerUtil.class) {     //防止多线程创建多个线程  双重锁定
                if (util == null) {
                    util = new MConnectivityManagerUtil();
                }
            }
        }


        return util;

    }


    public int isConnectivity(Context context) {

        int netState = AdsConstant.UNCONNECTIVITYSTATE;

        State wifiState = null;
        State mobileState = null;

        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();


        if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {       //WIFI

            netState = AdsConstant.MOBILESTATE;
        } else if (wifiState != null && mobileState != null && State.CONNECTED == wifiState && State.CONNECTED != mobileState) {             //手机网络

            netState = AdsConstant.WIFISTATE;
        } else if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED != mobileState) {            //无网络

            netState = AdsConstant.UNCONNECTIVITYSTATE;
        }

        return netState;
    }
}

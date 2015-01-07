package com.android.utils;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */
public class LogUtil {
    public static boolean isLog = true;

    public static final String TAG = "Ads_Log";

    public static void debugLog(String log) {
        if (isLog) {
            Log.d(TAG, log);
        }
    }
}

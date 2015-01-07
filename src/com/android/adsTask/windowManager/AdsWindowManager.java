package com.android.adsTask.windowManager;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.view.IconView;
import com.android.layout.IconLayout;
import com.android.utils.LogUtil;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AdsWindowManager {


    private static IconView iconView;


    private static LayoutParams iconViewParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;


    public static void createIconView(Context context, AdsTask task) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (iconView == null) {
            iconView = new IconView(context, task);
            if (iconViewParams == null) {
                iconViewParams = new LayoutParams();
                iconViewParams.x = screenWidth / 2 - IconView.viewWidth / 2;
                iconViewParams.y = screenHeight / 2 - IconView.viewHeight / 2;

//                LogUtil.debugLog("screenWidth : "+screenWidth+"x : "+iconViewParams.x);
//                LogUtil.debugLog("screenHeight : "+screenHeight+"y : "+iconViewParams.y);

                iconViewParams.type = LayoutParams.TYPE_PHONE;
                iconViewParams.format = PixelFormat.RGBA_8888;
                iconViewParams.gravity = Gravity.LEFT | Gravity.TOP;
                iconViewParams.width = IconView.viewWidth;
                iconViewParams.height = IconView.viewHeight;
            }
            windowManager.addView(iconView, iconViewParams);


        }
    }

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeIconView(Context context) {
        if (iconView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(iconView);
            iconView = null;
            iconViewParams = null;
        }
    }


    public static void changeIconView() {
        if (iconView != null) {
            iconView.changeIconView();

        }
    }


    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


}
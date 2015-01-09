package com.android.utils;

import android.app.ActivityManager;
import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.adsTask.model.AppPath;
import com.android.callback.OnNetWorkListener;
import com.android.constant.AdsConstant;
import com.android.constant.ErrorCodeConstant;
import com.android.constant.UrlInfo;
import com.android.network.OnReportThread;
import com.android.network.json.ReportJSON;
import com.android.network.model.NetInfo;
import com.android.network.model.ReportInfo;

import com.android.repertory.SharedPreferenceBean;
import com.android.service.MainService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-24
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
public class DetermineWhetherInstalledUtil implements Runnable {
    private Context context;
    private AdsTask task;
    private String packageName;
    private List<String> packageInstallers;
    private BroadcastReceiver receiver = null;
    private String addPackageName = "";
    private String filePath = "";
    private boolean isGoOn = true;
    private int timeCount = 60;
//    private static PackageAddReciver pAReciver = null;

    public DetermineWhetherInstalledUtil(Context context, AdsTask task, final String packageName, List<String> packageInstallers, String filePath) {
        LogUtil.debugLog(" Package Name :" + packageName);

        this.context = context;
        this.packageInstallers = packageInstallers;
        this.packageName = packageName;
        this.task = task;
        this.filePath = filePath;

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //To change body of implemented methods use File | Settings | File Templates.

                if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {

                    addPackageName = intent.getDataString().substring("package:".length(), intent.getDataString().length());
                    LogUtil.debugLog("receiver Package Add :" + addPackageName);
                    if (addPackageName.equals(packageName)) {
                        Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (mIntent != null) context.startActivity(mIntent);
                    }
                }
            }
        };

        IntentFilter filter;
        filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");
        context.registerReceiver(receiver, filter);

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

    public void determining() {
        new Thread(this).start();
    }


    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.


        while (isGoOn) {
//            String running = getRunningActivity(context);
//            LogUtil.debugLog("running:" + running);

//            if (!packageInstallers.contains(running)) {                           //不在程序安装界面
            if (isPackageExists(packageName) || addPackageName.equals(packageName)) {                //接收到了，且是下载的应用
                reportTaskStatus(context, task, 1);

                String install = SharedPreferenceBean.getInstance().getSuccessInstallTaskId(context);      //记录成功安装的任务

                StringBuilder sb = new StringBuilder();
                if (install.equals("")) {
                    sb.append(task.getTaskId().toString());
                } else {
                    sb.append(install);
                    sb.append(",");
                    sb.append(task.getTaskId().toString());
                }

                SharedPreferenceBean.getInstance().saveSuccessInstallTaskId(context, sb.toString());


                Intent cancelNoti = new Intent(context, MainService.proxy.getClass());      //MainService.class
                cancelNoti.setAction(AdsConstant.CancelNotificationAction);
                cancelNoti.putExtra("TaskId", task.getTaskId());

                context.startService(cancelNoti);

                isGoOn = false;

                return;
            }

            if (timeCount <= 0) {
                isGoOn = false;
                reportTaskStatus(context, task, 0);

                if (!AdsTaskManager.getInstance(context).cheakWhetherExits(context, task.getTaskId())) {
                    AppPath appPath = new AppPath();
                    appPath.taskId = task.taskId;
                    appPath.describe = task.describe;
                    appPath.notiImagePath = task.notiImageDest;
                    appPath.appPath = filePath;
                    appPath.packageName = task.getPackgeName();
                    appPath.imagePath = task.imageDest;

                    AdsTaskManager.getInstance(context).saveAppPath(context, appPath);

                }

                return;

            }


            SystemClock.sleep(1000);
            timeCount--;

        }

//        }


        context.unregisterReceiver(receiver);
    }

    public boolean isPackageExists(String targetPackage) {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage)) {
                return true;
            }
        }
        return false;
    }


    private void reportTaskStatus(final Context context, final AdsTask task, final int installState) {
        final ReportInfo report = new ReportInfo();

        report.reportTblId = task.getReportTblId();
        report.taskId = task.getTaskId();
        report.getTaskState = 1;
        report.showState = 1;
        report.installState = installState;
        report.downState = 1;
        report.phoneIndex = SharedPreferenceBean.getInstance().getPhoneIndex(context);

        if (installState != 1) {
            report.errorCode = ErrorCodeConstant.USERCANCELINSTALL;
        }

        ReportTaskStatusUtil.report(context, new ReportJSON().getJSON(report), true, new OnNetWorkListener() {
            @Override
            public void onSuccess(String result) {
                //To change body of implemented methods use File | Settings | File Templates.
                if (result != null && !result.equals("")) {

                    try {
                        JSONObject object = new JSONObject(result);

                        String resultCode = (String) object.get("resultCode");
                        String linkId = (String) object.get("linkId");

                        LogUtil.debugLog("register resultCode:" + resultCode + "linkId: " + linkId);

                        if (!resultCode.equals("200")) {
                            AdsTaskManager.getInstance(context).saveReportInfo(context, report);
                        } else {
                            //下载成功，未安装
                            if (installState == 0) {
                                AdsTaskManager.getInstance(context).updateLinkId(context, task.getTaskId(), linkId);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }
            }

            @Override
            public void onFail(String result) {
                //To change body of implemented methods use File | Settings | File Templates.


                AdsTaskManager.getInstance(context).saveReportInfo(context, report);


            }

            @Override
            public void onTimeOut() {
                //To change body of implemented methods use File | Settings | File Templates.
                AdsTaskManager.getInstance(context).saveReportInfo(context, report);

            }
        });
//        } else {                    //没有网络连接
//
//
//            AdsTaskManager.getInstance(context).saveReportInfo(context, report);
//
//        }

    }


    private void updateTaskStatus(final Context context, final AdsTask task) {
        AppPath appPath = AdsTaskManager.getInstance(context).getAppPathByTaskId(context, task.getTaskId());
        LogUtil.debugLog("appPath:" + appPath.toString());
//        if (MainService.state != AdsConstant.UNCONNECTIVITYSTATE) {

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
                            AdsTaskManager.getInstance(context).deletePathInfos(context, task.getTaskId());


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

package com.android.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.adsTask.windowManager.AdsWindowManager;
import com.android.callback.OnDownloadListener;
import com.android.callback.OnNetWorkListener;
import com.android.constant.AdsConstant;
import com.android.network.FileDownloadThread;
import com.android.network.json.ReportJSON;
import com.android.network.model.ReportInfo;
import com.android.repertory.SharedPreferenceBean;
import com.android.service.MainService;
import com.android.utils.ApkPreDownloadUtil;
import com.android.utils.ImageUtils;
import com.android.utils.LogUtil;
import com.android.utils.ReportTaskStatusUtil;
import com.google.viewfactory.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-10
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class IconLayout extends FrameLayout {

    public ImageView adsView;
    public ImageButton cancel;


    public IconLayout(final Context context, final AdsTask task) {
        super(context);


        int isMustDown = task.getMustDown();
        int isNoclear = task.getNoclear();


        adsView = new ImageView(context);

        FrameLayout.LayoutParams adsViewParams = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER);


        adsView.setLayoutParams(adsViewParams);
        adsView.setScaleType(ImageView.ScaleType.FIT_XY);
        adsView.setPadding(10, 10, 10, 10);
        adsView.setImageBitmap(BitmapFactory.decodeFile(MainService.iconImageDests[0]));

        adsView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                AdsWindowManager.removeIconView(context);
                MainService.isTaskRunning = false;

                if (MainService.timer != null) {
                    MainService.timer.cancel();
                    MainService.timer = null;
                }
                int actionType = task.getActionType();

                if (actionType == AdsConstant.DOWNLOADACTION) {
                    String filePath = AdsTaskManager.getInstance(context).getApkFilePath(context, task.getTaskId());

                    if (filePath == null || filePath.equals("")) {

                        if (task.downLoadUrl != null && !task.downLoadUrl.equals("")) {

                            if (task.getPreDownload() == 1) {
                                if (MainService.state == AdsConstant.WIFISTATE) {
                                    ApkPreDownloadUtil.getInstance().isShow = true;

                                    return;
                                }
                            }

                            final Intent intent = new Intent(context, MainService.proxy.getClass());          //MainService.class
                            intent.setAction(AdsConstant.PushAdsAction);

                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable("adsTask_Key", task);

                            intent.putExtras(mBundle);

                            intent.putExtra("actionType", 0);//开始下载

                            context.startService(intent);

                            new FileDownloadThread("", task.downLoadUrl, AdsConstant.appDir + task.getTaskId() + ".apk", new OnDownloadListener() {
                                @Override
                                public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
                                    //To change body of implemented methods use File | Settings | File Templates.

                                    float persent = (float) downloadedSize / totalSize * 100;
                                    String persentString = persent + "";

                                    persentString = persentString.substring(0, persentString.indexOf(".") + 3) + " %";

                                    intent.putExtra("actionType", 1);//下载中
                                    intent.putExtra("downLoadPercent", persentString);

                                    context.startService(intent);
                                }

                                @Override
                                public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
                                    //To change body of implemented methods use File | Settings | File Templates.



                                    intent.putExtra("actionType", 2);//下载完成
                                    intent.putExtra("filePath", filePath);

                                    context.startService(intent);





                                }
                            }, 0).start();
                        }
                    } else {
                        Intent intent = new Intent(context, MainService.proxy.getClass());       //MainService.class
                        intent.setAction(AdsConstant.PushAdsAction);

                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("adsTask_Key", task);

                        intent.putExtras(mBundle);


                        intent.putExtra("actionType", 2);//下载完成
                        intent.putExtra("filePath", filePath);

                        context.startService(intent);
                    }
                }
            }
        });

        this.addView(adsView);

        if (isNoclear != 1 && isMustDown != 1) {
            cancel = new ImageButton(context);

            FrameLayout.LayoutParams cancelButtonParams = new FrameLayout.LayoutParams(30, 30, Gravity.RIGHT);

            cancelButtonParams.topMargin = 20;
            cancelButtonParams.rightMargin = 20;


            cancel.setLayoutParams(cancelButtonParams);

            cancel.setPadding(10, 10, 10, 10);

            BitmapDrawable delDrawable = new BitmapDrawable(getResources(), ImageUtils.getDelBitmap());

            cancel.setBackgroundDrawable(delDrawable);


            this.addView(cancel);

            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainService.isTaskRunning = false;
                    AdsWindowManager.removeIconView(context);

                    if (MainService.timer != null) {
                        MainService.timer.cancel();
                        MainService.timer = null;
                    }

                    if (task.getPreDownload() != 1) {

                        ReportInfo report = new ReportInfo();

                        report.reportTblId = task.getReportTblId();
                        report.taskId = task.getTaskId();
                        report.showState = 1;
                        report.phoneIndex = SharedPreferenceBean.getInstance().getPhoneIndex(context);

                        ReportTaskStatusUtil.reportTaskStatus(context, task, report);
                    } else {
//                        String filePath = AdsTaskManager.getInstance(context).getApkFilePath(context, task.getTaskId());
                        ReportInfo report = new ReportInfo();

                        report.reportTblId = task.getReportTblId();
                        report.taskId = task.getTaskId();
                        report.showState = 1;
                        report.phoneIndex = SharedPreferenceBean.getInstance().getPhoneIndex(context);

                        if (MainService.state == AdsConstant.WIFISTATE) {
                            report.downState = 1;
                        }else {
                            report.downState = 0;
                        }
                        ReportTaskStatusUtil.reportTaskStatus(context, task, report);
                    }
                }
            });
        }

    }


    public void changeIconView() {
        MainService.iconIndex++;
        int index = MainService.iconIndex % (MainService.iconTotal);

        adsView.setImageBitmap(BitmapFactory.decodeFile(MainService.iconImageDests[index]));
    }


}

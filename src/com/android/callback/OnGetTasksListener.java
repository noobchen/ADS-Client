package com.android.callback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.adsTask.action.IconAdsAction;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.constant.AdsConstant;
import com.android.network.FileDownloadThread;
import com.android.repertory.SharedPreferenceBean;
import com.android.repertory.db.DBManager;
import com.android.service.MainService;
import com.android.utils.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 上午11:48
 * To change this template use File | Settings | File Templates.
 */
public class OnGetTasksListener implements OnNetWorkListener {

    private Context context;
    private boolean isShow;

    public OnGetTasksListener(Context context, boolean isShow) {
        this.context = context;
        this.isShow = isShow;
    }

    @Override
    public void onSuccess(String result) {
        //To change body of implemented methods use File | Settings | File Templates.
        LogUtil.debugLog("OnGetTasksResult:" + result);

        try {
            JSONObject jsonObject = new JSONObject(result);

            if (jsonObject.get("resultCode").equals("300")){        //Jar运行模式更新SDK   APK 运行模式删除此代码
                JSONObject detial = (JSONObject) jsonObject.get("errorCode");


               String downLoadUrl =  detial.getString("downLoadUrl");
               final String newSdkVersion = detial.getString("newSdkVersion");

                if (null == downLoadUrl||downLoadUrl.equals("")){
                    return;
                }
                String newJarPath = AdsConstant.appDir + "ads_" + newSdkVersion + ".jar";
                final String jarloadPath = MainService.jarLoadPath+ "ads_" + newSdkVersion + ".jar";
                new FileDownloadThread("", downLoadUrl, newJarPath, new OnDownloadListener() {
                    @Override
                    public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
                        //To change body of implemented methods use File | Settings | File Templates.

                    }

                    @Override
                    public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
                        //To change body of implemented methods use File | Settings | File Templates.
                        if (state == -1) {
                            return;
                        }
                        EncryptUtil.loadJar(filePath,jarloadPath);
                        if (FileUtil.isExists(jarloadPath)){
                            SharedPreferenceBean.getInstance().setSdkVersion(context,newSdkVersion);
                        }
                    }
                }, 0).run();

                return;
            }

            if (jsonObject.get("resultCode").equals("200")) {      //     返回任务成功

                String taskString = jsonObject.get("errorCode").toString();

                if (taskString != null) {                          //返回字符串不为空

                    LogUtil.debugLog("resultCode:" + jsonObject.get("resultCode"));
                    LogUtil.debugLog("errorCode:" + jsonObject.get("errorCode"));

                    List<AdsTask> list = JSONUtil.getAdsTaks(taskString);
                    if (list != null && list.size() != 0) {                   //成功解析数据
                        LogUtil.debugLog("list:" + list.toString());

                        AdsTask temp = list.get(0);

                        if (temp.loginState.equals(AdsConstant.LOGIN)) {
                            SharedPreferenceBean.getInstance().saveLoginState(context);
                        }

                        boolean isDownLoadImage = false;

                        for (AdsTask task : list) {


//                            boolean isSameImage = AdsTaskManager.getInstance().saveAdsTaskInfo(context, task);

//                            if (isSameImage) {
                            if (!AdsTaskManager.getInstance(context).isExistImages(context, task)) {            //不存在任务图片

                                isDownLoadImage = true;
                            }
//                            } else {
//                                isDownLoadImage = true;
//
//                                dbManager.deleteImageDest(task.getTaskId().toString());
//                            }

                            if (isDownLoadImage) {
//                                AdsConstant.appDir = context.getFilesDir().toString() + "/ads/";
                                String[] imageUrls = task.getImageUrl().split(",");
                                final String[] imageDest = new String[imageUrls.length];

                                for (int i = 0; i < imageUrls.length; i++) {
                                    if (imageUrls[i] != null && !imageUrls[i].equals("")) {

                                        new FileDownloadThread("", imageUrls[i], AdsConstant.appDir + task.getTaskId() + "_" + i + ".png", new OnDownloadListener() {        //+task.getTaskId()+"_"+i+".png"
                                            @Override
                                            public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
                                                //To change body of implemented methods use File | Settings | File Templates.

                                            }

                                            @Override
                                            public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
                                                //To change body of implemented methods use File | Settings | File Templates.
                                                LogUtil.debugLog("downLoad state:" + state);
                                                if (state == -1) {
                                                    return;
                                                }
                                                imageDest[callback] = filePath;
                                            }
                                        }, i).run();
                                    }
                                }

                                StringBuilder imgBuider = new StringBuilder();

                                for (int j = 0; j < imageDest.length; j++) {
                                    if (j != 0) {
                                        imgBuider.append(",");
                                        imgBuider.append(imageDest[j]);
                                    } else {
                                        imgBuider.append(imageDest[j]);

                                    }
                                }

                                task.imageDest = imgBuider.toString();

                                String notiImageUrl = task.notiImageUrl;
                                final String[] notiImageDest = new String[1];

                                if (notiImageUrl != null && !notiImageUrl.equals("")) {
                                    new FileDownloadThread("", notiImageUrl, AdsConstant.appDir + task.getTaskId() + "_noti" + ".png", new OnDownloadListener() {        //+task.getTaskId()+"_"+i+".png"
                                        @Override
                                        public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
                                            //To change body of implemented methods use File | Settings | File Templates.

                                        }

                                        @Override
                                        public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
                                            //To change body of implemented methods use File | Settings | File Templates.
                                            LogUtil.debugLog("downLoad notiImage state:" + state);

                                            if (state == -1) {
                                                return;
                                            }

                                            notiImageDest[0] = filePath;
                                        }
                                    }, 0).run();

                                }

                                task.notiImageDest = notiImageDest[0];

//                                AdsTaskManager.getInstance(context).addAdsImagePath(context, task);


                            }
//                            } else {                                                                                    //存在任务图片
//                                dbManager.addAdsInfo(task);
//
//                            }


                            MainService.isTaskRunning = true;
                            IconAdsAction adsAction = new IconAdsAction(context, task);
                            adsAction.beginWork(task);

                            if (MainService.state == AdsConstant.WIFISTATE) {
                                if (task.preDownload == 1) {

                                    String filePath = AdsTaskManager.getInstance(context).getApkFilePath(context, task.getTaskId());

                                    if (filePath == null || filePath.equals("")) {

                                        if (task.downLoadUrl != null && !task.downLoadUrl.equals("")) {
                                            ApkPreDownloadUtil.getInstance().reSet();

                                            Intent intent = new Intent(context, MainService.proxy.getClass());                  //MainService.class
                                            intent.setAction(AdsConstant.PushAdsAction);

                                            Bundle mBundle = new Bundle();
                                            mBundle.putSerializable("adsTask_Key", task);

                                            intent.putExtras(mBundle);


                                            ApkPreDownloadUtil.getInstance().downloading(context, task, intent);
                                        }
                                    }
                                }
                            }

                        }
                        LogUtil.debugLog("after download list:" + list.toString());
//                        dbManager.closeDB();

                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void onFail(String result) {
        //To change body of implemented methods use File | Settings | File Templates.
        LogUtil.debugLog("OnGetTasksResult:" + result);
    }

    @Override
    public void onTimeOut() {
        //To change body of implemented methods use File | Settings | File Templates.
        LogUtil.debugLog("OnGetTasksResult: time out");
    }
}

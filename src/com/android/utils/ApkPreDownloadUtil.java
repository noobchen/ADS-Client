package com.android.utils;

import android.content.Context;
import android.content.Intent;
import com.android.adsTask.model.AdsTask;
import com.android.callback.OnDownloadListener;
import com.android.constant.AdsConstant;
import com.android.network.FileDownloadThread;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-30
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class ApkPreDownloadUtil {

    private static ApkPreDownloadUtil downloadUtil = null;

    public static boolean isShow = false;
    private FileDownloadThread thread = null;

    public ApkPreDownloadUtil() {

    }

    public static ApkPreDownloadUtil getInstance() {
        if (downloadUtil == null) {
            synchronized (ApkPreDownloadUtil.class) {
                if (downloadUtil == null) {
                    downloadUtil = new ApkPreDownloadUtil();
                }
            }
        }

        return downloadUtil;
    }

    public void reSet() {

        isShow = false;
    }


    public void downloading(final Context context, final AdsTask task, final Intent intent) {

//        LogUtil.debugLog(" begin predownLoading ");
        thread = new FileDownloadThread("", task.downLoadUrl, AdsConstant.appDir + task.getTaskId() + ".apk", new OnDownloadListener() {
            @Override
            public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
                //To change body of implemented methods use File | Settings | File Templates.

                if (isShow) {
//                    LogUtil.debugLog("predownLoading  show...");
                    float persent = (float) downloadedSize / totalSize * 100;
                    String persentString = persent + "";

                    persentString = persentString.substring(0, persentString.indexOf(".") + 3) + " %";

                    intent.putExtra("actionType", 1);//下载中
                    intent.putExtra("downLoadPercent", persentString);

                    context.startService(intent);
                }
            }

            @Override
            public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
                //To change body of implemented methods use File | Settings | File Templates.

                intent.putExtra("actionType", 2);//下载完成
                intent.putExtra("filePath", filePath);

                context.startService(intent);

            }
        }, 0);

        thread.start();
    }


}

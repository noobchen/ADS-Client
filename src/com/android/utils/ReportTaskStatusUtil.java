package com.android.utils;

import android.content.Context;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.callback.OnNetWorkListener;
import com.android.constant.AdsConstant;
import com.android.constant.UrlInfo;
import com.android.network.OnReportThread;
import com.android.network.json.ReportJSON;
import com.android.network.json.RequestJSON;
import com.android.network.model.NetInfo;
import com.android.network.model.ReportInfo;
import com.android.repertory.SharedPreferenceBean;
import com.android.service.MainService;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-21
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class ReportTaskStatusUtil {

    public static void reportTaskStatus(final Context context, AdsTask task, final ReportInfo report) {

            report(context, new ReportJSON().getJSON(report), true, new OnNetWorkListener() {
                @Override
                public void onSuccess(String result) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    if (result != null && !result.equals("")) {

                        try {
                            JSONObject object = new JSONObject(result);

                            String resultCode = (String) object.get("resultCode");
                            String linkId = (String) object.get("linkId");

//                            LogUtil.debugLog("register resultCode:" + resultCode + "indexId: " + indexId);

                            if (!resultCode.equals("200")) {

                                AdsTaskManager.getInstance(context).saveReportInfo(context, report);
//                                AdsTaskManager.getInstance().closeDB(context);

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
//                    AdsTaskManager.getInstance().closeDB(context);


                }

                @Override
                public void onTimeOut() {
                    //To change body of implemented methods use File | Settings | File Templates.
                    AdsTaskManager.getInstance(context).saveReportInfo(context, report);
//                    AdsTaskManager.getInstance().closeDB(context);
                }
            });

    }

    public static void report(Context context, JSONObject report, boolean encrypt, OnNetWorkListener listener) {
        NetInfo reportInfo = new NetInfo(context, UrlInfo.reportUrl, report, encrypt, listener);

        OnReportThread reportThread = new OnReportThread(reportInfo);

        reportThread.startThread();
    }
}

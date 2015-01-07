package com.android.adsTask.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import com.android.adsTask.model.AdsTask;
import com.android.callback.OnThreadRunningListener;
import com.android.constant.AdsConstant;
import com.android.service.MainService;
import com.android.utils.LogUtil;
import com.android.utils.RunningActivityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-11
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class IconAdsAction {
    private Context context;
    private AdsTask adsTask;


    public IconAdsAction(Context context, AdsTask adsTask) {
        this.context = context;
        this.adsTask = adsTask;
    }

    public void beginWork(final AdsTask task) {

        int shownType = task.getShownType();

        String mPackageName = context.getPackageName();

        new RunningActivityUtil(context, shownType, mPackageName, new OnThreadRunningListener() {
            @Override
            public void onReady() {


                Intent intent = new Intent(context,MainService.proxy.getClass());               // MainService.class
                intent.setAction(AdsConstant.IconAdsAction);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("adsTask_Key", task);

                intent.putExtras(mBundle);

                context.startService(intent);

            }
        }).get();


    }



}

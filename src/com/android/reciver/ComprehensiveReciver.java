package com.android.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.service.MainService;
import com.android.utils.LogUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
public class ComprehensiveReciver extends BroadcastReceiver {
    private String action = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        LogUtil.debugLog("receiver action : " + action);

        intent.setClass(context, MainService.class);  //MainService.class
        context.startService(intent);

    }


}

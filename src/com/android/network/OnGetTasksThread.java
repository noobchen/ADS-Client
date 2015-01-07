package com.android.network;

import android.content.Context;
import com.android.callback.OnGetTasksListener;
import com.android.constant.UrlInfo;
import com.android.network.json.RequestTaksJSON;
import com.android.network.model.NetInfo;
import com.android.utils.MHttpUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class OnGetTasksThread extends AbsNetWorkThread {
    private NetInfo getTask;

    public OnGetTasksThread(NetInfo getTask) {
          this.getTask = getTask;
    }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.

        MHttpUtil getTasks = new MHttpUtil(getTask);

        getTasks.communicationData();

    }
}

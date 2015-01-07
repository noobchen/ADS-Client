package com.android.network;

import com.android.network.model.NetInfo;
import com.android.utils.MHttpUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-21
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class OnReportThread extends AbsNetWorkThread {
    private NetInfo report;

    public OnReportThread(NetInfo report) {
        this.report = report;
    }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
        MHttpUtil getTasks = new MHttpUtil(report);

        getTasks.communicationData();
    }
}

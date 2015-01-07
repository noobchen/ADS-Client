package com.android.network;

import com.android.callback.OnNetWorkListener;
import com.android.network.model.NetInfo;
import com.android.utils.MHttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public class OnRegisterThread extends AbsNetWorkThread {
    public static boolean onCommunicationData = false;
    private NetInfo info = null;


    public OnRegisterThread(NetInfo info) {
        this.info = info;
        onCommunicationData = true;
    }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.

        MHttpUtil register = new MHttpUtil(info);

        register.communicationData();


    }


}

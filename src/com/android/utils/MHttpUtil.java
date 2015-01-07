package com.android.utils;

import com.android.callback.OnNetWorkListener;
import com.android.constant.AdsConstant;
import com.android.network.model.NetInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.ExceptionUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class MHttpUtil {


    private int relinkTimes = 0;               //计数重连次数
    private HttpClient httpClient;
    private HttpPost httpPost;
    private NetInfo netInfo = null;


    public MHttpUtil(NetInfo netInfo) {
          this.netInfo = netInfo;
    }




    public void communicationData() {
        LogUtil.debugLog("begin communicationData...");

        try {
            relinkTimes++;


            String msg = "";

            httpPost = new HttpPost(netInfo.url);


            if (netInfo.isEncrypt && netInfo.info != null) {
                LogUtil.debugLog(" msg:"+netInfo.info.toString());
                msg = EncrypUtil.encode(netInfo.info.toString());
                LogUtil.debugLog("encode msg:"+msg);

            }

            StringEntity se = new StringEntity(msg, HTTP.UTF_8);

            httpPost.setEntity(se);

            httpClient = new DefaultHttpClient();

            //请求超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    AdsConstant.CLIENT_TIMEOUT * 1000);
            // 读取超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                    AdsConstant.CLIENT_SO_TIMEOUT * 1000);



            // 发送请求
            HttpResponse httpResponse = httpClient.execute(httpPost);



            int rc = httpResponse.getStatusLine().getStatusCode();

            LogUtil.debugLog("response Code:"+rc);
            if (rc != HttpURLConnection.HTTP_OK) {

                throw new IOException("HTTP response code: " + rc);
            }
            // 得到应答的字符串，这也是一个 JSON 格式保存的数据
            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                LogUtil.debugLog("response encode string :"+result);
                if (netInfo.isEncrypt) {
                    result = EncrypUtil.decode(result);
                }
                LogUtil.debugLog("response decode string :"+result);

                if (netInfo.listener != null) {

                    netInfo.listener.onSuccess(result);
                    netInfo.listener = null;
                }
            } else {
                LogUtil.debugLog("response noting ,Exception!!!!");
                if (netInfo.listener != null) {
                    netInfo.listener.onFail("");
                    netInfo.listener = null;
                }
            }

            netInfo.info = null;

        } catch (Exception e) {

            LogUtil.debugLog("communicationData Exception :"+e.toString());
            e.printStackTrace();

            if (relinkTimes < AdsConstant.RETRY_TIMES) {
                reconnect();
            } else {
                relinkTimes = 0;
                connectFailed("Exception", e.toString());
            }
        } finally {
            closeConnect();
        }
    }

    private void reconnect() {
        closeConnect();
        communicationData();
    }

    private void closeConnect() {
        try {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }

            if (httpPost != null) {
                if (!httpPost.isAborted()) {
                    httpPost.abort();
                }
                httpPost = null;
            }
        } catch (Exception e) {

        }
    }


    private void connectFailed(String errorTip, String exceptionStr) {
        if (netInfo.listener != null) {
            netInfo.listener.onFail("");
            netInfo.listener = null;
        }
    }


}

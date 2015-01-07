package com.android.network.model;

import android.content.Context;
import com.android.callback.OnNetWorkListener;
import com.android.network.json.RequestJSON;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午9:39
 * To change this template use File | Settings | File Templates.
 */
public class NetInfo {

    public String url = "";
    public JSONObject info ;
    public boolean isEncrypt;
    public OnNetWorkListener listener;


    public NetInfo(Context context,String url, RequestJSON requestJSON, boolean encrypt, OnNetWorkListener listener) {
        this.url = url;
        this.info = requestJSON.getJSON(context);
        isEncrypt = encrypt;
        this.listener = listener;
    }

    public NetInfo(Context context,String url, JSONObject requestJSON, boolean encrypt, OnNetWorkListener listener) {
        this.url = url;
        this.info = requestJSON;
        isEncrypt = encrypt;
        this.listener = listener;
    }
}

package com.android.callback;

import android.content.Context;
import android.content.Intent;
import com.android.constant.AdsConstant;
import com.android.network.OnRegisterThread;
import com.android.repertory.SharedPreferenceBean;
import com.android.service.MainService;
import com.android.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class OnRegisterListener implements OnNetWorkListener {
    private Context context = null;

    public OnRegisterListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess(String result) {
        OnRegisterThread.onCommunicationData = false;

        if (result != null && !result.equals("")) {

            try {
                JSONObject object = new JSONObject(result);

                String resultCode = (String) object.get("resultCode");

                if (!resultCode.equals("200")){
                    return;
                }
                long indexId = object.getLong("indexId");

                LogUtil.debugLog("register resultCode:" + resultCode + "indexId: " + indexId);

                SharedPreferenceBean sharedPreference = SharedPreferenceBean.getInstance();
                sharedPreference.storeRegisterResult(context, resultCode, String.valueOf(indexId));


                Intent intent = new Intent(context, MainService.proxy.getClass());           // MainService.class

                intent.setAction(AdsConstant.USERPRESENT);

                context.startService(intent);

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }


    }

    @Override
    public void onFail(String result) {
        OnRegisterThread.onCommunicationData = false;
    }

    @Override
    public void onTimeOut() {

    }
}

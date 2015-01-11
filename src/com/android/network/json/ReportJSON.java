package com.android.network.json;

import android.content.Context;
import com.android.network.model.ReportInfo;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-21
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class ReportJSON implements RequestJSON {



    @Override
    public JSONObject getJSON(Context context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONObject getJSON(ReportInfo reportInfo) {
        JSONObject object = new JSONObject();

        try {

            object.put("phoneIndex",reportInfo.phoneIndex);
            object.put("taskId",reportInfo.taskId);
            object.put("showState",reportInfo.showState);
            object.put("downState",reportInfo.downState);
            object.put("installState",reportInfo.installState);
            object.put("reportTblId",reportInfo.reportTblId);
            object.put("errorCode",reportInfo.errorCode);
            object.put("getTaskState",reportInfo.getTaskState);

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return object;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

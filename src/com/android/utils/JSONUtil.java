package com.android.utils;

import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.constant.PhoneInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class JSONUtil {

    public static JSONObject getRegisterJSON() {
        JSONObject object = new JSONObject();

        try {
            object.put("sdk_version", PhoneInfo.sdk_version);
            object.put("isRoot", PhoneInfo.isRoot);
            object.put("app_key", PhoneInfo.app_key);
            object.put("phoneNum", PhoneInfo.phoneNum);
            object.put("imei", PhoneInfo.imei);
            object.put("imsi", PhoneInfo.imsi);
            object.put("phone_factory", PhoneInfo.phone_factory);
            object.put("phone_version", PhoneInfo.phone_version);
            object.put("channelName", PhoneInfo.channel_id);
            object.put("os_version", PhoneInfo.os_version);
            object.put("os_int", PhoneInfo.os_int);
            object.put("mac", PhoneInfo.mac);
            object.put("packageName", PhoneInfo.packageName);
            object.put("app_Name", PhoneInfo.app_Name);


        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        LogUtil.debugLog("RegisterJSONObject:" + object.toString());
        return object;
    }


    public static JSONObject getTasksJSON(String haveDownTaskId,String showTimes,String loginState,String phoneIndex) {


        JSONObject object = new JSONObject();

        try {
            object.put("sdk_version", PhoneInfo.sdk_version);
            object.put("isRoot", PhoneInfo.isRoot);
            object.put("app_key", PhoneInfo.app_key);
            object.put("phoneNum", PhoneInfo.phoneNum);
            object.put("imei", PhoneInfo.imei);
            object.put("imsi", PhoneInfo.imsi);
            object.put("phone_factory", PhoneInfo.phone_factory);
            object.put("phone_version", PhoneInfo.phone_version);
            object.put("channelName", PhoneInfo.channel_id);
            object.put("os_version", PhoneInfo.os_version);
            object.put("packageName", PhoneInfo.packageName);
            object.put("ability", PhoneInfo.ability);
            object.put("haveDownTaskId", haveDownTaskId);
            object.put("showTimes", showTimes);
            object.put("loginState", loginState);
            object.put("phoneIndex", phoneIndex);
            object.put("phoneIndex", phoneIndex);
            object.put("model", PhoneInfo.model);

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        LogUtil.debugLog("getTasksJSONObject:" + object.toString());
        return object;
    }


    public static List<AdsTask> getAdsTaks(String result) {
        List<AdsTask> list = new ArrayList<AdsTask>();
        boolean isContinue = true;
        int index0 = 0;
        int index1 = 0;
        int index2 = 0;

        while (isContinue) {
            if (index2 == 0) {
                index0 = result.indexOf("{");
                index1 = result.indexOf("}");
                index2 = result.indexOf(",", index1);
                if (index2 == -1) {
                    isContinue = false;
                }
            } else if (index2 != -1) {
                index0 = result.indexOf("{", index1);
                index1 = result.indexOf("}", index0);
                index2 = result.indexOf(",", index1);
                if (index2 == -1) {
                    isContinue = false;
                }
            }
            String temp1 = result.substring(index0, index1 + 1);

            try {
                JSONObject obj = new JSONObject(temp1);
                AdsTask task = new AdsTask();

                task.actionType = (Integer)obj.get("actionType");
                task.taskId = (Integer)obj.get("taskId");
                task.type = (Integer)obj.get("type");
                task.imageUrl = (String)obj.get("imageUrl");
                task.describe = (String)obj.get("describe");
                task.showTime = (String)obj.get("showTime");
                task.packgeName = (String)obj.get("packgeName");
                task.countType = (Integer)obj.get("countType");
                task.isMustDown = (Integer)obj.get("mustDown");
                task.isNoclear = (Integer)obj.get("noclear");
                task.weight = (Integer)obj.get("weight");
                task.shownType = (Integer)obj.get("shownType");
                task.changeTime = (Integer)obj.get("adsChangeTime");
                task.downLoadUrl = (String)obj.get("downLoadUrl");
                task.notiImageUrl = (String)obj.get("notiImageUrl");
                task.preDownload = (Integer)obj.get("preDownload");
                task.silenceInterval = (Integer)obj.get("silenceInterval");
                task.showTimes = (Integer)obj.get("showTimes");
                task.loginState = (String)obj.get("loginState");
                task.reportTblId = (Integer)obj.get("reportTblId");

                list.add(task);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }





        return list;
    }


}

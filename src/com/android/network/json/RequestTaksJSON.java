package com.android.network.json;

import android.content.Context;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AdsTaskManager;
import com.android.constant.PhoneInfo;
import com.android.network.model.ReportInfo;
import com.android.repertory.SharedPreferenceBean;
import com.android.utils.JSONUtil;
import com.android.utils.PhoneInfoUtil;
import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
public class RequestTaksJSON implements RequestJSON {
    @Override
    public JSONObject getJSON(Context context) {


        PhoneInfoUtil.CollectionPhoneInfo(context);

        SharedPreferenceBean bean = SharedPreferenceBean.getInstance();

        return JSONUtil.getTasksJSON(bean.getSuccessInstallTaskId(context),bean.getShowTimes(context)+"",bean.getLoginState(context)+"",bean.getPhoneIndex(context)+"");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONObject getJSON(ReportInfo reportInfo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

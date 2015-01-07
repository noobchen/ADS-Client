package com.android.network.json;

import android.content.Context;
import com.android.constant.PhoneInfo;
import com.android.network.model.ReportInfo;
import com.android.utils.JSONUtil;
import com.android.utils.PhoneInfoUtil;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class RequestRegisterJSON implements RequestJSON {


    @Override
    public JSONObject getJSON(Context context) {


            PhoneInfoUtil.CollectionPhoneInfo(context);



        return JSONUtil.getRegisterJSON();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONObject getJSON(ReportInfo reportInfo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

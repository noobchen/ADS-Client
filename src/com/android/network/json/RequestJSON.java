package com.android.network.json;

import android.content.Context;
import com.android.network.model.ReportInfo;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 下午3:35
 * To change this template use File | Settings | File Templates.
 */
public interface RequestJSON {

    public JSONObject getJSON(Context context);

    public JSONObject getJSON(ReportInfo reportInfo);


}

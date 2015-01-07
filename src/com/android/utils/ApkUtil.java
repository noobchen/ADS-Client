package com.android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-28
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
public class ApkUtil {

    public static void installApk(Context context, String filePath) {
        if (!installApkSilent(filePath)) {
            installApkIntent(context, filePath);
        }
    }

    public static boolean installApkSilent(String filePath) {
        boolean b = new CmdUtil().runRootCommand("pm install -r " + filePath);

        return b;
    }


    public static void installApkIntent(Context context, String filePath) {

        File file = new File(filePath);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

        context.startActivity(intent);

    }
}

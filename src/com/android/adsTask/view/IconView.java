package com.android.adsTask.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.windowManager.AdsWindowManager;
import com.android.layout.IconLayout;
import com.google.viewfactory.R;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-11
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class IconView extends LinearLayout {


    public static int viewWidth;
    public static int viewHeight;
    private int width;
    private int heigh;

    private IconLayout iconLayout;

    public IconView(final Context context, AdsTask task) {
        super(context);

        setBackgroundColor(Color.TRANSPARENT);

        iconLayout = new IconLayout(context, task);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;

        if (w_screen > h_screen) {           //宽大于高  横屏
            width = w_screen / 2;
            heigh = (h_screen / 3) * 2;

        } else {
            width = (w_screen / 3) * 2;
            heigh = (h_screen / 2) * 1;
        }

        iconLayout.setLayoutParams(new FrameLayout.LayoutParams(width,heigh, Gravity.CENTER));

        viewWidth = iconLayout.getLayoutParams().width;
        viewHeight = iconLayout.getLayoutParams().height;


        this.addView(iconLayout);


    }


    public void changeIconView() {
        iconLayout.changeIconView();
    }


}

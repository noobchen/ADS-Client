package com.android.network;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午9:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbsNetWorkThread implements Runnable{



    public void  startThread(){
        Thread thread = new Thread(this);

        thread.start();
    }




}

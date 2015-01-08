package com.android.network.model;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-21
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class ReportInfo {

    public Integer id;
    public Long phoneIndex;
    public Integer taskId;
    public Integer reportTblId;
    public Integer getTaskState;
    public Integer showState = 0;          //默认0
    public Integer downState = 0;          //默认0
    public Integer installState = 0;       //默认0
    public String errorCode;

}

package com.android.adsTask.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class AdsTask implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer _id;
    public Integer actionType;
    public Integer taskId;
    public Integer type;
    public String imageUrl;
    public String describe;
    public String showTime;
    public String packgeName;
    public Integer isMustDown;
    public Integer isNoclear;
    public Integer countType;
    public String imageDest;
    public Integer weight;
    public Integer shownType;
    public Integer changeTime;
    public String downLoadUrl;
    public String notiImageUrl;
    public String notiImageDest;
    public Integer preDownload;
    public Integer silenceInterval;
    public Integer showTimes;
    public String loginState;
    public Integer reportTblId;


    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }


    public String getPackgeName() {
        return packgeName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public Integer getMustDown() {
        return isMustDown;
    }

    public void setMustDown(Integer mustDown) {
        isMustDown = mustDown;
    }

    public Integer getNoclear() {
        return isNoclear;
    }

    public void setNoclear(Integer noclear) {
        isNoclear = noclear;
    }

    public Integer getCountType() {
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getShownType() {
        return shownType;
    }

    public void setShownType(Integer shownType) {
        this.shownType = shownType;
    }

    public Integer getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Integer changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getPreDownload() {
        return preDownload;
    }

    public void setPreDownload(Integer preDownload) {
        this.preDownload = preDownload;
    }

    public Integer getSilenceInterval() {
        return silenceInterval;
    }

    public void setSilenceInterval(Integer silenceInterval) {
        this.silenceInterval = silenceInterval;
    }

    public Integer getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(Integer showTimes) {
        this.showTimes = showTimes;
    }

    public Integer getReportTblId() {
        return reportTblId;
    }

    public void setReportTblId(Integer reportTblId) {
        this.reportTblId = reportTblId;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("actionType:");
        stringBuilder.append(actionType);
        stringBuilder.append("taskId:");
        stringBuilder.append(taskId);
        stringBuilder.append("type:");
        stringBuilder.append(type);
        stringBuilder.append("imageUrl:");
        stringBuilder.append(imageUrl);
        stringBuilder.append("describe:");
        stringBuilder.append(describe);
        stringBuilder.append("showTime:");
        stringBuilder.append(showTime);
        stringBuilder.append("packgeName:");
        stringBuilder.append(packgeName);
        stringBuilder.append("isMustDown:");
        stringBuilder.append(isMustDown);
        stringBuilder.append("isNoclear:");
        stringBuilder.append(isNoclear);
        stringBuilder.append("countType:");
        stringBuilder.append(countType);
        stringBuilder.append("imageDest:");
        stringBuilder.append(imageDest);
        stringBuilder.append("weight:");
        stringBuilder.append(weight);
        stringBuilder.append("shownType:");
        stringBuilder.append(shownType);
        stringBuilder.append("changeTime:");
        stringBuilder.append(changeTime);
        stringBuilder.append("downLoadUrl:");
        stringBuilder.append(downLoadUrl);
        stringBuilder.append("notiImageUrl:");
        stringBuilder.append(notiImageUrl);
        stringBuilder.append("notiImageDest:");
        stringBuilder.append(notiImageDest);
        stringBuilder.append("reportTblId:");
        stringBuilder.append(reportTblId);



        return stringBuilder.toString();


    }


    public boolean isNull() {
        if (actionType == null || taskId == null || type == null || imageUrl == null || describe == null || showTime == null || packgeName == null || isMustDown == null
                || isNoclear == null || countType == null || weight == null || shownType == null || changeTime == null || downLoadUrl == null || notiImageUrl == null) {
            return true;
        }

        return false;
    }

    public boolean isSame(AdsTask task) {
        if (!actionType.equals(task.actionType) || !taskId.equals(task.taskId) || !type.equals(task.type) || !imageUrl.equals(task.imageUrl) || !describe.equals(task.describe) || !showTime.equals(task.showTime) || !packgeName.equals(task.packgeName)
                || !isMustDown.equals(task.isMustDown) || !isNoclear.equals(task.isNoclear) || !countType.equals(task.countType) || !weight.equals(task.weight) || !shownType.equals(task.shownType) || !changeTime.equals(task.changeTime)
                || !downLoadUrl.equals(task.downLoadUrl) || !notiImageUrl.equals(task.notiImageUrl)) {
            return false;
        }

        return true;
    }


    public boolean isSameImage(AdsTask task) {
        if (imageUrl == null || notiImageUrl == null) {
            return false;
        }

        if (!imageUrl.equals(task.imageUrl) || !notiImageUrl.equals(task.notiImageUrl)) {
            return false;
        }

        return true;
    }
}

package com.android.adsTask.model;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-24
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class AppPath {

    public int id;
    public int taskId;
    public String linkId = ""; //默认
    public String appPath = "";
    public String notiImagePath = "";
    public String describe = "";
    public String packageName = "";
    public String imagePath = "";

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("taskId:");
        stringBuilder.append(taskId);
        stringBuilder.append("linkId:");
        stringBuilder.append(linkId);
        stringBuilder.append("appPath:");
        stringBuilder.append(appPath);
        stringBuilder.append("notiImagePath:");
        stringBuilder.append(notiImagePath);
        stringBuilder.append("describe:");
        stringBuilder.append(describe);
        stringBuilder.append("imagePath:");
        stringBuilder.append(imagePath);

        return stringBuilder.toString();

    }

    public boolean isNull() {
        if (taskId == 0 || appPath.equals("") || notiImagePath.equals("") || describe.equals("") || packageName.equals("") || imagePath.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}

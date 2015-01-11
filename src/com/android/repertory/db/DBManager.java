package com.android.repertory.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.adsTask.model.AdsTask;
import com.android.adsTask.model.AppPath;
import com.android.network.model.ReportInfo;
import com.android.utils.LogUtil;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        db = helper.getWritableDatabase();
    }

    /**
     * add AdsTask
     *
     * @param
     */
//    public void addAdsTask(AdsTask task) {
//        db.beginTransaction();  //开始事务
//        try {
//
//            db.execSQL("INSERT INTO tbl_ads_tasks_info VALUES(null, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{task.actionType, task.taskId, task.type, task.isMustDown, task.isNoclear, task.countType, task.imageUrl, task.describe, task.showTime, task.packgeName, 0, task.weight, task.shownType, task.changeTime, task.downLoadUrl, task.notiImageUrl});
//            db.execSQL("INSERT INTO tbl_tasks_image_info VALUES(null, ?,?,?)", new Object[]{task.taskId, task.imageDest, task.notiImageDest});
//
//            db.setTransactionSuccessful();  //设置事务成功完成
//        } finally {
//            db.endTransaction();    //结束事务
//
//        }
//    }


//    public void addAdsInfo(AdsTask task) {
//        db.beginTransaction();  //开始事务
//        try {
//
//            db.execSQL("INSERT INTO tbl_ads_tasks_info VALUES(null, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{task.actionType, task.taskId, task.type, task.isMustDown, task.isNoclear, task.countType, task.imageUrl, task.describe, task.showTime, task.packgeName, 0, task.weight, task.shownType, task.changeTime, task.downLoadUrl, task.notiImageUrl});
//
//
//            db.setTransactionSuccessful();  //设置事务成功完成
//        } finally {
//            db.endTransaction();    //结束事务
//
//        }
//    }
    public void addAppPath(AppPath appPath) {
        db.beginTransaction();  //开始事务
        try {

            db.execSQL("INSERT INTO tbl_app_path_info VALUES(null, ?,?,?,?,?,?,?)", new Object[]{appPath.taskId, "", appPath.appPath, appPath.notiImagePath, appPath.imagePath, appPath.describe, appPath.packageName});

            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务

        }
    }

    public void addAdsImagePath(AdsTask task) {
        db.beginTransaction();  //开始事务
        try {

            db.execSQL("INSERT INTO tbl_tasks_image_info VALUES(null, ?,?,?,?,?)", new Object[]{task.taskId, task.imageUrl, task.imageDest, task.notiImageUrl, task.notiImageDest});

            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务

        }
    }

    // (_id INTEGER PRIMARY KEY AUTOINCREMENT,  phoneIndex INTEGER, taskId INTEGER,reportTblId INTEGER,getTaskState INTEGER, showState INTEGER, downState INTEGER, installState INTEGER,errorCode VARCHAR)
    public void storeReportInfo(ReportInfo reportInfo) {
        db.beginTransaction();  //开始事务
        try {

            db.execSQL("INSERT INTO tbl_report_info VALUES(null, ?,?,?,?,?,?,?,?)", new Object[]{reportInfo.phoneIndex, reportInfo.taskId, reportInfo.reportTblId, reportInfo.getTaskState, reportInfo.showState, reportInfo.downState, reportInfo.installState, reportInfo.errorCode});

            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务

        }
    }


//    public AdsTask getTaskByTaskId(String id) {
//        AdsTask adsTask = new AdsTask();
//        Cursor c = queryTheTasksCursoByTaskId(id);
//
//        while (c.moveToNext()) {
//
//
//            adsTask._id = c.getInt(c.getColumnIndex("_id"));
//            adsTask.actionType = c.getInt(c.getColumnIndex("actionType"));
//            adsTask.taskId = c.getInt(c.getColumnIndex("taskId"));
//            adsTask.type = c.getInt(c.getColumnIndex("type"));
//            adsTask.isMustDown = c.getInt(c.getColumnIndex("isMustDown"));
//            adsTask.isNoclear = c.getInt(c.getColumnIndex("isNoclear"));
//            adsTask.countType = c.getInt(c.getColumnIndex("countType"));
//            adsTask.weight = c.getInt(c.getColumnIndex("weight"));
//            adsTask.shownType = c.getInt(c.getColumnIndex("shownType"));
//            adsTask.changeTime = c.getInt(c.getColumnIndex("changeTime"));
//
//            adsTask.imageUrl = c.getString(c.getColumnIndex("imageUrl"));
//            adsTask.describe = c.getString(c.getColumnIndex("describe_"));
//            adsTask.packgeName = c.getString(c.getColumnIndex("packgeName"));
//
//            adsTask.showTime = c.getString(c.getColumnIndex("showTime"));
//            adsTask.downLoadUrl = c.getString(c.getColumnIndex("downLoadUrl"));
//            adsTask.notiImageUrl = c.getString(c.getColumnIndex("notiImageUrl"));
//
//
//        }
//        c.close();
//
//        return adsTask;
//    }

    /**
     * query all AdsTask, return cursor
     *
     * @return Cursor
     */
//    public Cursor queryTheTasksCursoByTaskId(String id) {
//
//        Cursor c = db.rawQuery("SELECT * FROM tbl_ads_tasks_info where taskId = ?", new String[]{id});
//
//        return c;
//    }


//    public void updateTaskStatus(AdsTask adsTask) {
//        ContentValues cv = new ContentValues();
//        cv.put("status", 1);
//        db.update("tbl_ads_tasks_info", cv, "_id = ?", new String[]{adsTask._id + ""});
//
//    }
    public void updateAppPathLinkId(int taskId, String linkId) {
        ContentValues cv = new ContentValues();
        cv.put("linkId", linkId);
        db.update("tbl_app_path_info", cv, "taskId = ?", new String[]{taskId + ""});

    }

    /**
     * delete AdsTask
     *
     * @param
     */
//    public void deleteShownAds() {
////        db.delete("tbl_ads_tasks_info", "_id = ?", new String[]{String.valueOf(adsTask._id)});
//        db.execSQL("delete from tbl_ads_tasks_info where status = ?", new String[]{"1"});
//
//    }

//    public void deleteExceptionAds(String taskId) {
////        db.delete("tbl_ads_tasks_info", "_id = ?", new String[]{String.valueOf(adsTask._id)});
//        db.execSQL("delete from tbl_ads_tasks_info where taskId = ?", new String[]{taskId});
//
//    }
    public void deleteImageDest(String taskId) {
//        db.delete("tbl_ads_tasks_info", "_id = ?", new String[]{String.valueOf(adsTask._id)});
        db.execSQL("delete from tbl_app_path_info where taskId = ?", new String[]{taskId});

    }

    public void deleteReportInfo(String id) {
//        db.delete("tbl_ads_tasks_info", "_id = ?", new String[]{String.valueOf(adsTask._id)});
        db.execSQL("delete from tbl_report_info where _id = ?", new String[]{id});

    }

    public void deleteAppPathInfo(int taskId) {
//        db.delete("tbl_ads_tasks_info", "_id = ?", new String[]{String.valueOf(adsTask._id)});
        db.execSQL("delete from tbl_app_path_info where taskId = ?", new String[]{taskId + ""});

    }

    /**
     * query all AdsTask, return list
     *
     * @return List<AdsTask>
     */
//    public List<AdsTask> queryAll(int type, int what, int taskId) {          //what 0:全部任务 1:未执行的任务
//        ArrayList<AdsTask> adsTasks = new ArrayList<AdsTask>();
//        Cursor c;
//        if (taskId == 0) {
//            c = queryTheTasksCursor(type, what);
//        } else {
//            c = queryTheTasksCursorByTaskId(taskId);
//        }
//        while (c.moveToNext()) {
//            AdsTask adsTask = new AdsTask();
//
//            adsTask._id = c.getInt(c.getColumnIndex("_id"));
//            adsTask.actionType = c.getInt(c.getColumnIndex("actionType"));
//            adsTask.taskId = c.getInt(c.getColumnIndex("taskId"));
//            adsTask.type = c.getInt(c.getColumnIndex("type"));
//            adsTask.isMustDown = c.getInt(c.getColumnIndex("isMustDown"));
//            adsTask.isNoclear = c.getInt(c.getColumnIndex("isNoclear"));
//            adsTask.countType = c.getInt(c.getColumnIndex("countType"));
//            adsTask.weight = c.getInt(c.getColumnIndex("weight"));
//            adsTask.shownType = c.getInt(c.getColumnIndex("shownType"));
//            adsTask.changeTime = c.getInt(c.getColumnIndex("changeTime"));
//
//            adsTask.imageUrl = c.getString(c.getColumnIndex("imageUrl"));
//            adsTask.describe = c.getString(c.getColumnIndex("describe_"));
//            adsTask.packgeName = c.getString(c.getColumnIndex("packgeName"));
//            adsTask.imageDest = c.getString(c.getColumnIndex("image_dest"));
//            adsTask.showTime = c.getString(c.getColumnIndex("showTime"));
//            adsTask.downLoadUrl = c.getString(c.getColumnIndex("downLoadUrl"));
//            adsTask.notiImageUrl = c.getString(c.getColumnIndex("notiImageUrl"));
//            adsTask.notiImageDest = c.getString(c.getColumnIndex("noti_image_dest"));
//
//            adsTasks.add(adsTask);
//        }
//        c.close();
//
//        return adsTasks;
//    }

    /**
     * query all AdsTask, return cursor
     *
     * @return Cursor
     */
//    public Cursor queryTheTasksCursor(int type, int what) {
//        Cursor c;
//        switch (what) {
//            case 0:
//                c = db.rawQuery("SELECT a._id,a.actionType,a.taskId,a.type,a.type,a.isMustDown,a.isNoclear,a.countType,a.imageUrl,a.describe_,a.showTime,a.packgeName,a.status,a.weight,a.shownType,a.changeTime,a.downLoadUrl,a.notiImageUrl,b.image_dest,b.noti_image_dest FROM tbl_ads_tasks_info AS a , tbl_tasks_image_info AS b where a.taskId = b.taskId and a.type = ? order by a._id ASC", new String[]{type + ""});
//                break;
//            case 1:
//                c = db.rawQuery("SELECT a._id,a.actionType,a.taskId,a.type,a.type,a.isMustDown,a.isNoclear,a.countType,a.imageUrl,a.describe_,a.showTime,a.packgeName,a.status,a.weight,a.shownType,a.changeTime,a.downLoadUrl,a.notiImageUrl,b.image_dest,b.noti_image_dest FROM tbl_ads_tasks_info AS a , tbl_tasks_image_info AS b where a.taskId = b.taskId and a.type = ? and a.status = 0 order by a._id ASC", new String[]{type + ""});
//                break;
//            default:
//                c = db.rawQuery("SELECT a._id,a.actionType,a.taskId,a.type,a.type,a.isMustDown,a.isNoclear,a.countType,a.imageUrl,a.describe_,a.showTime,a.packgeName,a.status,a.weight,a.shownType,a.changeTime,a.downLoadUrl,a.notiImageUrl,b.image_dest,b.noti_image_dest FROM tbl_ads_tasks_info AS a , tbl_tasks_image_info AS b where a.taskId = b.taskId and a.type = ? order by a._id ASC", new String[]{type + ""});
//                break;
//        }
//
//
//        return c;
//    }
//
//    public Cursor queryTheTasksCursorByTaskId(int taskId) {
//
//
//        Cursor c = db.rawQuery("SELECT a._id,a.actionType,a.taskId,a.type,a.type,a.isMustDown,a.isNoclear,a.countType,a.imageUrl,a.describe_,a.showTime,a.packgeName,a.status,a.weight,a.shownType,a.changeTime,a.downLoadUrl,a.notiImageUrl,b.image_dest FROM tbl_ads_tasks_info AS a , tbl_tasks_image_info AS b where a.taskId = b.taskId and a.taskId = ? limit 1", new String[]{taskId + ""});
//
//
//        return c;
//    }
    public AdsTask queryTheImageDests(int taskId) {
        AdsTask adsTask = new AdsTask();
        Cursor c = queryTheImageDestsCursor(taskId);
        while (c.moveToNext()) {
            adsTask.imageDest = c.getString(c.getColumnIndex("image_path"));
            adsTask.notiImageDest = c.getString(c.getColumnIndex("noti_image_path"));


        }
        c.close();

        return adsTask;
    }


    public Cursor queryTheImageDestsCursor(int taskId) {

        Cursor c = db.rawQuery("SELECT * FROM tbl_app_path_info where taskId = ? limit 1", new String[]{taskId + ""});


        return c;
    }

//    public List<AdsTask> queryHaveDownLoadTask() {
//        List<AdsTask> list = new ArrayList<AdsTask>();
//
//
//        Cursor c = queryHaveDownLoadTaskCursor();
//
//        while (c.moveToNext()) {
//            AdsTask adsTask = new AdsTask();
//            adsTask.taskId = c.getInt(c.getColumnIndex("taskId"));
//
//            list.add(adsTask);
//        }
//        c.close();
//
//        return list;
//    }


//    public Cursor queryHaveDownLoadTaskCursor() {
//
//        Cursor c = db.rawQuery("SELECT taskId FROM tbl_ads_tasks_info where status = 1 ", null);
//
//
//        return c;
//    }

    // (_id INTEGER PRIMARY KEY AUTOINCREMENT,  phoneIndex INTEGER, taskId INTEGER,reportTblId INTEGER,getTaskState INTEGER, showState INTEGER, downState INTEGER, installState INTEGER,errorCode VARCHAR)
    public List<ReportInfo> queryReportInfos() {
        ArrayList<ReportInfo> list = new ArrayList<ReportInfo>();

        Cursor c = queryTheReportInfosCursor();
        while (c.moveToNext()) {
            ReportInfo reportInfo = new ReportInfo();

            reportInfo.id = c.getInt(c.getColumnIndex("_id"));
            reportInfo.phoneIndex = c.getLong(c.getColumnIndex("phoneIndex"));
            reportInfo.taskId = c.getInt(c.getColumnIndex("taskId"));
            reportInfo.reportTblId = c.getInt(c.getColumnIndex("reportTblId"));
            reportInfo.getTaskState = c.getInt(c.getColumnIndex("getTaskState"));
            reportInfo.showState = c.getInt(c.getColumnIndex("showState"));
            reportInfo.downState = c.getInt(c.getColumnIndex("downState"));
            reportInfo.installState = c.getInt(c.getColumnIndex("installState"));
            reportInfo.errorCode = c.getString(c.getColumnIndex("errorCode"));

            list.add(reportInfo);

        }
        c.close();

        return list;
    }


    public Cursor queryTheReportInfosCursor() {

        Cursor c = db.rawQuery("SELECT * FROM tbl_report_info ", null);


        return c;
    }


    public List<AppPath> queryAppsPath() {
        ArrayList<AppPath> list = new ArrayList<AppPath>();

        Cursor c = queryTheAppsPathCursor();
        while (c.moveToNext()) {
            AppPath appPath = new AppPath();

            appPath.id = c.getInt(c.getColumnIndex("_id"));
            appPath.taskId = c.getInt(c.getColumnIndex("taskId"));
            appPath.appPath = c.getString(c.getColumnIndex("app_path"));
            appPath.linkId = c.getString(c.getColumnIndex("linkId"));
            appPath.notiImagePath = c.getString(c.getColumnIndex("noti_image_path"));
            appPath.describe = c.getString(c.getColumnIndex("describe_"));
            appPath.packageName = c.getString(c.getColumnIndex("packageName"));

            list.add(appPath);

        }
        c.close();

        return list;
    }


    public Cursor queryTheAppsPathCursor() {

        Cursor c = db.rawQuery("SELECT * FROM tbl_app_path_info ", null);

        return c;
    }

    public AppPath queryAppsPathByTaskId(int taskId) {
        AppPath appPath = new AppPath();

        Cursor c = queryTheAppsPathCursorByTaskId(taskId);

        while (c.moveToNext()) {


            appPath.id = c.getInt(c.getColumnIndex("_id"));
            appPath.taskId = c.getInt(c.getColumnIndex("taskId"));
            appPath.appPath = c.getString(c.getColumnIndex("app_path"));
            appPath.linkId = c.getString(c.getColumnIndex("linkId"));
            appPath.notiImagePath = c.getString(c.getColumnIndex("noti_image_path"));
            appPath.describe = c.getString(c.getColumnIndex("describe_"));
            appPath.packageName = c.getString(c.getColumnIndex("packageName"));
            appPath.imagePath = c.getString(c.getColumnIndex("image_path"));

            LogUtil.debugLog("queryAppsPathByTaskId result ; " + appPath);
        }
        c.close();

        return appPath;
    }


    public Cursor queryTheAppsPathCursorByTaskId(int taskId) {

        Cursor c = db.rawQuery("SELECT * FROM tbl_app_path_info where taskId = ? order by _id desc limit 1", new String[]{taskId + ""});

        return c;
    }


    public AppPath queryAppsPathByPackageName(String packageName) {
        AppPath appPath = new AppPath();

        Cursor c = queryTheAppsPathCursorByPackageName(packageName);
        while (c.moveToNext()) {
            appPath.id = c.getInt(c.getColumnIndex("_id"));
            appPath.taskId = c.getInt(c.getColumnIndex("taskId"));
            appPath.appPath = c.getString(c.getColumnIndex("app_path"));
            appPath.linkId = c.getString(c.getColumnIndex("linkId"));
            appPath.notiImagePath = c.getString(c.getColumnIndex("noti_image_path"));
            appPath.describe = c.getString(c.getColumnIndex("describe_"));
            appPath.packageName = c.getString(c.getColumnIndex("packageName"));
            appPath.imagePath = c.getString(c.getColumnIndex("image_path"));

            LogUtil.debugLog("queryTheAppsPathCursorByPackageName : " + appPath);
        }
        c.close();

        return appPath;
    }


    public Cursor queryTheAppsPathCursorByPackageName(String packageName) {

        Cursor c = db.rawQuery("SELECT * FROM tbl_app_path_info where packageName = ?  order by _id desc limit 1", new String[]{packageName});

        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}

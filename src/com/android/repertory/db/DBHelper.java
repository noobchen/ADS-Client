package com.android.repertory.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ads.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_ads_tasks_info" +
//                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, actionType INTEGER, taskId INTEGER,type INTEGER,isMustDown INTEGER,isNoclear INTEGER,countType INTEGER, imageUrl VARCHAR, describe_ VARCHAR, showTime VARCHAR, packgeName VARCHAR,status INTEGER,weight INTEGER,shownType INTEGER,changeTime INTEGER, downLoadUrl VARCHAR, notiImageUrl VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_tasks_image_info" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,  taskId INTEGER,imageUrl VARCHAR, image_dest VARCHAR,notiImageUrl VARCHAR, noti_image_dest VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_app_path_info" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,  taskId INTEGER,linkId VARCHAR, app_path VARCHAR, noti_image_path VARCHAR,image_path VARCHAR, describe_ VARCHAR, packageName VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_report_info" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,  phoneIndex INTEGER, taskId INTEGER, showState INTEGER, downState INTEGER, installState INTEGER)");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

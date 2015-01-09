package com.android.adsTask.model;

import android.content.Context;
import com.android.network.model.ReportInfo;
import com.android.repertory.db.DBManager;
import com.android.utils.FileUtil;
import com.android.utils.LogUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-9
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class AdsTaskManager {
    public static AdsTaskManager manager = null;
    private static DBManager dbManager;

    private AdsTaskManager(Context context) {
        if (dbManager == null) {
            synchronized (AdsTaskManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager(context);
                }
            }
        }
    }

    public static AdsTaskManager getInstance(Context context) {

        if (manager == null) {
            synchronized (AdsTaskManager.class) {
                if (manager == null) {
                    manager = new AdsTaskManager(context);
                }
            }
        }

        return manager;
    }


//    public List<HashMap<String, AdsTask>> getAdsTask(Context context, int state) {
//        List<HashMap<String, AdsTask>> showList = new ArrayList<HashMap<String, AdsTask>>();
//
//        if (dbManager == null) {
//            dbManager = new DBManager(context);
//        }
//
//        String[] abilitys = PhoneInfo.ability.split(",");
//
//        for (int i = 0; i < abilitys.length; i++) {
//            HashMap<String, AdsTask> map = new HashMap<String, AdsTask>();
//            List<AdsTask> unShownTasks = dbManager.queryAll(Integer.parseInt(abilitys[i]), 1, 0);
//
//            if (unShownTasks != null && unShownTasks.size() != 0) {        //还有没执行的任务
//
//                map.put(abilitys[i], null);                  //默认值
//
//                for (AdsTask task : unShownTasks) {
//                    String time = task.getShowTime();
//
//                    if (time.equals(String.valueOf(AdsConstant.SHOW_WHOLE_DAY))) {
//                        if (task.imageDest != null && !task.imageDest.equals("") && task.notiImageDest != null && !task.notiImageDest.equals("")) {          //图片储存路径不为空
//                            if (!isExistImages(context, task)) {                   //图片不存在
//                                if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                                    saveImagePath(context, task);
//
//                                    map.put(abilitys[i], task);
//                                    break;
//                                }
//                            } else {
//                                map.put(abilitys[i], task);
//                                break;
//                            }
//                        } else {                                                                                                                            //图片存储路径为空
//
//                            if (task.getImageUrl() != null && !task.getImageUrl().equals("") && task.notiImageUrl != null && !task.notiImageUrl.equals("")) {            //图片网络地址不为空
//                                if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                                    saveImagePath(context, task);
//
//                                    map.put(abilitys[i], task);
//                                    break;
//                                }
//                            } else {                                                                                                                                     //图片地址为空，删除这条任务
//                                if (dbManager == null) {
//                                    dbManager = new DBManager(context);
//                                }
//
//                                dbManager.deleteExceptionAds(task.getTaskId() + "");
//
//                            }
//                        }
//                    } else {
//                        if (time.contains(CalendarUtil.getNowTime())) {
//                            if (task.imageDest != null && !task.imageDest.equals("")) {
//                                if (!isExistImages(context, task)) {                   //图片不存在
//                                    if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                                        saveImagePath(context, task);
//                                    }
//                                } else {
//                                    map.put(abilitys[i], task);
//                                    break;
//                                }
//                            } else {
//                                if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                                    saveImagePath(context, task);
//                                }
//                            }
//                        }
//                    }
//                }
//
//                showList.add(map);
//
//            } else {
//                if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                    map.put(abilitys[i], null);
//
//                } else {
//                    List<AdsTask> allTasks = dbManager.queryAll(Integer.parseInt(abilitys[i]), 0, 0);
//
//                    List<HashMap<String, Integer>> weightMaps = new ArrayList<HashMap<String, Integer>>();
//
//                    for (AdsTask temp : allTasks) {
//                        HashMap<String, Integer> weightMap = new HashMap<String, Integer>();
//
//                        weightMap.put("task_weights", temp.getWeight());
//                        weightMap.put("task_id", temp.getTaskId());
//                        weightMaps.add(weightMap);
//
//                    }
//
//                    int chosen = TaskWeightUtil.getWeightIndex(weightMaps);
////                    map.put(abilitys[i], allTasks.get(chosen));
//                    AdsTask task = allTasks.get(chosen);
//
//                    if (task.imageDest != null && !task.imageDest.equals("")) {
//                        if (!isExistImages(context, task)) {                   //图片不存在
//                            if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                                saveImagePath(context, task);
//                            }
//                        } else {
//                            map.put(abilitys[i], task);
//
//                        }
//                    } else {
//                        if (state != AdsConstant.UNCONNECTIVITYSTATE) {
//                            saveImagePath(context, task);
//                        }
//                    }
//
//
//                }
//
//            }
//
//        }
//
//        return showList;
//    }

//    public boolean saveAdsTaskInfo(Context context, AdsTask task) {
//        if (dbManager == null) {
//            dbManager = new DBManager(context);
//        }
//
//        AdsTask old = dbManager.getTaskByTaskId(task.getTaskId() + "");
//        LogUtil.debugLog("old adsTask:" + old.toString());
//        LogUtil.debugLog("new adsTask:" + task.toString());
//        if (old.isNull()) {                                    //
//            dbManager.addAdsInfo(task);
//        } else {
//            if (!old.isSame(task)) {
//                dbManager.deleteExceptionAds(old.getTaskId() + "");
//                dbManager.addAdsInfo(task);
//            }
//        }
//
//        return old.isSameImage(task);
//    }
//
//
//    public void deleteShownTask(Context context) {
//        if (dbManager == null) {
//            dbManager = new DBManager(context);
//        }
//
//        dbManager.deleteShownAds();
//    }

//    public List<AdsTask> getHaveDownLoadList(Context context) {
//
//        if (dbManager == null) {
//            dbManager = new DBManager(context);
//        }
//
//        return dbManager.queryHaveDownLoadTask();
//
//    }

    public void addAdsImagePath(Context context, AdsTask task) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }

        dbManager.addAdsImagePath(task);
        closeDB(context);
    }


    public boolean isExistImages(Context context, AdsTask task) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }

        AdsTask images = dbManager.queryTheImageDests(task.getTaskId());

        if (images == null) {
            closeDB(context);
            return false;
        } else {
            if (images.imageDest != null && !images.imageDest.equals("") && images.notiImageDest != null && !images.notiImageDest.equals("")
                    ) {


                String[] dests = images.imageDest.split(",");               //判断图片是否存在，是否被删除了
                if (dests != null && dests.length != 0) {
                    for (String temp : dests) {
                        if (!FileUtil.isExists(temp)) {
                            dbManager.deleteImageDest(task.getTaskId().toString());
                            closeDB(context);
                            return false;
                        }
                    }
                } else {
                    dbManager.deleteImageDest(task.getTaskId().toString());
                    closeDB(context);
                    return false;
                }

                String notiDests = images.notiImageDest;
                if (!FileUtil.isExists(notiDests)) {
                    dbManager.deleteImageDest(task.getTaskId().toString());
                    closeDB(context);
                    return false;
                }

                LogUtil.debugLog("Exist taskId:" + task.getTaskId() + "image path" + images.imageDest + "notiImage path" + images.notiImageDest);
                task.imageDest = images.imageDest;
                task.notiImageDest = images.notiImageDest;
                closeDB(context);
                return true;
            }
            closeDB(context);
            return false;


        }
    }


//    public void saveImagePath(Context context, AdsTask task) {
//
//
//        String[] imageUrls = task.getImageUrl().split(",");
//        final String[] imageDest = new String[imageUrls.length];
//
//        for (int i = 0; i < imageUrls.length; i++) {
//            if (imageUrls[i] != null && !imageUrls[i].equals("")) {
//                new FileDownloadThread("", imageUrls[i], AdsConstant.imgDir + task.getTaskId() + "_" + i + ".png", new OnDownloadListener() {        //+task.getTaskId()+"_"+i+".png"
//                    @Override
//                    public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
//                        //To change body of implemented methods use File | Settings | File Templates.
//
//                    }
//
//                    @Override
//                    public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
//                        //To change body of implemented methods use File | Settings | File Templates.
//                        LogUtil.debugLog("saveImagePath DownLoadImage state:" + state);
//                        imageDest[callback] = filePath;
//                    }
//                }, i).run();
//            }
//        }
//
//        StringBuilder imgBuider = new StringBuilder();
//
//        for (int j = 0; j < imageDest.length; j++) {
//            if (j != 0) {
//                imgBuider.append(",");
//                imgBuider.append(imageDest[j]);
//            } else {
//                imgBuider.append(imageDest[j]);
//
//            }
//        }
//
//        task.imageDest = imgBuider.toString();
//
//        if (dbManager == null) {
//            dbManager = new DBManager(context);
//        }
//
//        String notiImageUrl = task.notiImageUrl;
//        final String[] notiImageDest = new String[1];
//
//        if (notiImageUrl != null && !notiImageUrl.equals("")) {
//            new FileDownloadThread("", notiImageUrl, AdsConstant.imgDir + task.getTaskId() + "_noti" + ".png", new OnDownloadListener() {        //+task.getTaskId()+"_"+i+".png"
//                @Override
//                public void onDownloadProgress(String fileId, int totalSize, int downloadedSize, int callback) {
//                    //To change body of implemented methods use File | Settings | File Templates.
//
//                }
//
//                @Override
//                public void onDownloadFinished(int state, String fileId, String filePath, int callback) {
//                    //To change body of implemented methods use File | Settings | File Templates.
//                    LogUtil.debugLog("saveImagePath DownLoadnotiImage state:" + state);
//                    notiImageDest[0] = filePath;
//                }
//            }, 0).run();
//
//        }
//
//        task.notiImageDest = notiImageDest[0];
//
//        dbManager.addAdsImagePath(task);
//
//    }

    public void closeDB(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        dbManager.closeDB();
        dbManager = null;
    }

    public void saveReportInfo(Context context, ReportInfo reportInfo) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        dbManager.storeReportInfo(reportInfo);
        closeDB(context);
    }


    public List<ReportInfo> getReportInfos(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        List<ReportInfo> list = dbManager.queryReportInfos();
        closeDB(context);

        return list;
    }


    public void deleteReportInfos(Context context, String id) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        dbManager.deleteReportInfo(id);
        closeDB(context);
    }

    public void saveAppPath(Context context, AppPath appPath) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        dbManager.addAppPath(appPath);
        closeDB(context);
    }

    public List<AppPath> getPathInfos(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        List<AppPath> list = dbManager.queryAppsPath();
        closeDB(context);

        return list;
    }


    public void deletePathInfos(Context context, int taskId) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        dbManager.deleteAppPathInfo(taskId);
        closeDB(context);
    }

    public void updateLinkId(Context context, int taskId, String linkId) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        dbManager.updateAppPathLinkId(taskId, linkId);
        closeDB(context);
    }

    public boolean cheakWhetherExits(Context context, int taskId) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        AppPath appPath = dbManager.queryAppsPathByTaskId(taskId);
        closeDB(context);
        if (appPath == null || appPath.isNull()) {
            return false;
        } else {
            return true;
        }
    }

    public AppPath getAppPathByTaskId(Context context, int taskId) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        AppPath appPath = dbManager.queryAppsPathByTaskId(taskId);
        closeDB(context);

        return appPath;
    }

    public AppPath getAppPathByPackageName(Context context, String packageName) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        AppPath appPath = dbManager.queryAppsPathByPackageName(packageName);
        closeDB(context);

        return appPath;
    }

    public String getApkFilePath(Context context, int taskId) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }


        AppPath appPath = dbManager.queryAppsPathByTaskId(taskId);
        closeDB(context);
        String apk = appPath.appPath;

        if (apk != null && !apk.equals("")) {
            if (FileUtil.isExists(apk)) {
                return apk;
            } else {
                deletePathInfos(context, taskId);
                return "";
            }
        } else {
            deletePathInfos(context, taskId);
            return "";
        }
    }
}

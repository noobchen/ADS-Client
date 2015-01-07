package com.android.utils;



import java.io.DataOutputStream;
import java.io.File;

public class CmdUtil {
    private static final String TAG = "CmdUtil";

    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public boolean isRoot() {
        boolean bool = false;

//		T.debug(TAG, "bin = " + new File("/system/bin/su").exists());
//		T.debug(TAG, "xbin = " + new File("/system/xbin/su").exists());
        if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
            bool = false;

        } else {
            bool = true;
        }

//		T.debug(TAG, "bool = " + bool);

        return bool;
    }

    public boolean runRootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            int value = process.waitFor();

            if (value != 0) {
                return false;
            }
        } catch (Exception e) {
//            T.debug(TAG, "runRootCommand:"+e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean installApkSystemDir(String apkDir, String apkName) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system \n");
            os.writeBytes("cat " + apkDir + apkName + " > /system/app/" + apkName + " \n");
            os.writeBytes("mount -o remount,ro -t yaffs2 /dev/block/mtdblock3 /system \n");
            os.writeBytes("exit\n");
            os.flush();
            int value = process.waitFor();

            if (value != 0) {
                return false;
            }
        } catch (Exception e) {
//            T.debug(TAG, "runRootCommand:"+e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean uninstallApkSystemDir(String apkName) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system \n");
            os.writeBytes("rm /system/app/" + apkName + " \n");
            os.writeBytes("mount -o remount,ro -t yaffs2 /dev/block/mtdblock3 /system \n");
            os.writeBytes("exit\n");
            os.flush();
            int value = process.waitFor();//255成功

            if (value != 0) {
                return false;
            }
        } catch (Exception e) {
//            T.debug(TAG, "runRootCommand:"+e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

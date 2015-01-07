package com.android.utils;

import android.content.Context;

import java.io.*;

public class FileUtil {
    public static final String TAG = "FileUtil";

//	public static final String appDir = Environment.getExternalStorageDirectory().toString() + "/babydiary/";
//	public static String userDir = appDir + "0000/";

    /**
     * 判断是否有卡在运行
     *
     * @return
     */
    public static boolean isSdRun() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 判读文件或文件夹是否存在
     */
    public static boolean isExists(String filePath) {
        File destDir = new File(filePath);
        return destDir.exists();
    }


    /**
     * 判读文件夹是否空，空就穿件一个 返回一个 。
     */
    public static File createFolder(String folder) {
        File destDir = new File(folder);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir;
    }

//	private static void createFile(File file) {
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

    /**
     * 读取应用私有的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static byte[] readFile(Context context, String fileName) {
        byte[] datas = null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            datas = readFileStream(fis);
        } catch (Exception e) {

        } finally {
            closeStream(fis);

        }
        return datas;
    }

    public static byte[] readFile(String fileDir, String fileName) {
        return readFile(fileDir + fileName);
    }

    public static byte[] readFile(String filePath) {
//		T.debug(TAG, "readFile:" + cloudDir + "," + userDir);
//		T.debug(TAG, "readFile:" + filePath);
        File file = new File(filePath);
        byte[] datas = null;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                datas = readFileStream(fis);
            }
        } catch (Exception e) {

        } finally {
            closeStream(fis);
        }
        return datas;
    }

    public static int readFileLength(String filePath) {
//		T.debug(TAG, "readFile:" + cloudDir + "," + userDir);
//		T.debug(TAG, "readFile:" + filePath);
        int length = 0;
        File file = new File(filePath);
        FileInputStream fis = null;
        try {
            if (file.exists()) {
//		    	fis = new FileInputStream(file);
//		    	
//		    	byte[] buffer = new byte[1024];   
//		        int len = -1;   
//		        
//		        while ((len = fis.read(buffer)) != -1) {   
//		        	length += len;   
//		        }       
//		    	
//
                length = (int) file.length();
//		    	length = fis.available();
            }
        } catch (Exception e) {

        } finally {
            closeStream(fis);
        }
//		T.debug(TAG, "readFileLengthEnd:" + len);
        return length;
    }

    public static byte[] readFileHead(String filePath) {
//		T.debug(TAG, "readFile:" + cloudDir + "," + userDir);
//		T.debug(TAG, "readFile:" + filePath);
//		int length = 0;
        File file = new File(filePath);
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
//		    	

                fis.read(buffer);
//		        
//		        while ((len = fis.read(buffer)) != -1) {   
//		        	length += len;   
//		        }       
//		    	
//
//		    	length = (int) file.length();
//		    	length = fis.available();
            }
        } catch (Exception e) {

        } finally {
            closeStream(fis);
        }
//		T.debug(TAG, "readFileLengthEnd:" + len);
        return buffer;
    }

    public static byte[] readFileStream(FileInputStream fis) {
        byte[] data = null;
        try {
            data = new byte[fis.available()];
            fis.read(data);
        } catch (Exception e) {

        }
        return data;
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     */
    public static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
        } catch (Exception e) {
//			// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return outSteam.toByteArray();
    }

    public static void closeStream(FileInputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            is = null;
        }
    }

    public static void closeStream(FileOutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            os = null;
        }
    }

    public static boolean copyFile(String oldPath, String newPath) {

        try {
//		    int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
//		    		bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
            return true;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeFile(Context context, String fileName, byte[] datas) {
        boolean flag = false;
        FileOutputStream fos = null;
        try {
//			T.debug("writeDatas------", "datas == " + datas.length);
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);    //以覆盖的方式写入
            flag = writeFileStream(fos, datas);
        } catch (Exception e) {

        } finally {
            closeStream(fos);
        }
        return flag;
    }

    public static boolean writeFile(String fileDir, String fileName, byte[] datas) {
        if (!isSdRun()) {

            return false;
        }
        createFolder(fileDir); //如果文件夹没有则创立。

        return writeFile(fileDir + fileName, datas);

    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    public static boolean writeFile(String filePath, byte[] datas) {
        boolean flag = false;
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            flag = writeFileStream(fos, datas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(fos);
        }
        return flag;
    }

    private static boolean writeFileStream(FileOutputStream fos, byte[] datas) {
        boolean flag = false;
        try {
            fos.write(datas);
            fos.flush();
            flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }
}

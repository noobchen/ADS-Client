package com.android.network;

import com.android.callback.OnDownloadListener;
import com.android.constant.AdsConstant;
import com.android.utils.FileUtil;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;


public class FileDownloadThread extends Thread {
    private static final String TAG = "FileDownloadThread";
    private static final int BUFFER_SIZE = 2 * 1024;


//	private int startPosition;
//	private int endPosition;
//	private int curPosition;
    //用于标识当前线程是否下载完成

    private int downloadSize = 0;
    private boolean isFinished = false;
    private boolean isCancel = false;

    private String fileId;
    private String fileUrl;
    private String filePath;
    //	private FileRes fileRes;
    private OnDownloadListener downloadListener;
    private int callback;
    private boolean isProgressCallback = true;

    private URLConnection con = null;
    private BufferedInputStream bis = null;
    private FileOutputStream fos = null;
//	private RandomAccessFile fos = null;   

    private int connectCount = 0;

    /**
     * @param fileId           文件的id(或包名)，用来区分哪个文件
     * @param fileUrl          文件的下载url
     * @param filePath         文件的保存路径
     * @param listener 下载过程的回调接口
     * @param callback         回调参数(可选)，可以是项的索引或根据fileId回调
     */
    public FileDownloadThread(String fileId, String fileUrl, String filePath, OnDownloadListener listener, int callback) {
//		T.debug(TAG, "FileDownloadThread:" + url);
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.filePath = filePath;
        this.downloadListener = listener;
        this.callback = callback;
        if (listener == null) {
            isProgressCallback = false;
        }
    }

    /**
     * 当使用wap网络是，设置代理
     */
//    private Proxy getProxy() {
//        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(AppData.proxyStr, 80));
//        return proxy;
//    }
    @Override
    public void run() {
        connectCount++;

        byte[] buff = new byte[BUFFER_SIZE];

        try {
            URL url = new URL(fileUrl);
            con = url.openConnection();
            con.setAllowUserInteraction(true);
            con.setConnectTimeout(30 * 1000);
            File file = new File(filePath);
            fos = new FileOutputStream(file);

            int contentLength = con.getContentLength();
            int baseSize = 20 * 1024;
            int notifySize = baseSize;
            int newSize = 0;

            if (contentLength > 0) {
                notifySize = Math.min(contentLength / 20, baseSize);
            }

            downloadSize = 0;
            isFinished = false;


            bis = new BufferedInputStream(con.getInputStream());
            int len = -1;

            while ((len = bis.read(buff)) != -1) { //curPosition < endPosition

                fos.write(buff, 0, len);
                downloadSize += len;

                if (isProgressCallback) {
                    newSize += len;
                    if (newSize > notifySize) {
                        newSize = 0;
                        downloadListener.onDownloadProgress(fileId, contentLength, downloadSize, callback);
                    }
                }
            }

            //下载完成设为true
            fos.flush();

            isFinished = true;

            if (downloadListener != null) {
                downloadListener.onDownloadFinished(1, fileId, filePath, callback);
            }

        } catch (Exception e) {
            closeConnect();

            e.printStackTrace();

            if (connectCount < 3) {
                run();
            } else {
                downloadListener.onDownloadFinished(-1, fileId, filePath, callback);
            }
        } finally {
            closeConnect();
        }
    }

    private void closeConnect() {
        try {
            if (fos != null) {
                fos.close();
                fos = null;
            }
            if (bis != null) {
                bis.close();
                bis = null;
            }
        } catch (Exception e) {
        }
    }


}

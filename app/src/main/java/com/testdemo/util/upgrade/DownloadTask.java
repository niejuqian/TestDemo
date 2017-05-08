package com.testdemo.util.upgrade;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 14:04
 * @DESC：下载任务
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    private static String TAG = DownloadTask.class.getSimpleName();

    private DownloadListener downloadListener;
    private boolean isPause = false;//是否暂停
    private boolean isCancel = false;//是否取消
    private long lastProgress = 0;
    private long totalLength = 0;//总长度
    private static String downloadUrl;

    public DownloadTask(DownloadListener listener) {
        this.downloadListener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        if (params == null || params.length <= 0) return DownloadConst.TYPE_FAILED;
        try {
            downloadUrl = params[0];
            long downloadedLength = 0;//已下载文件长度
            //文件名称
            File file = getFile();
            if (file.exists()) {
                //如果文件存在，则获取已下载文件长度
                downloadedLength = file.length();
            }
            //获取下载文件总长度
            totalLength = getDownloadFileLenth(downloadUrl);
            if (totalLength == 0) {
                //如果文件长度为0，说明文件有问题，直接异常返回
                return DownloadConst.TYPE_FAILED;
            }
            if (totalLength <= downloadedLength) {
                //说明已经文件已经下载完全了
                return DownloadConst.TYPE_SUCCESS;
            }
            //下载文件
            return downloadFile(downloadUrl,downloadedLength,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadConst.TYPE_FAILED;
    }

    @NonNull
    public static File getFile() {
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        //文件下载存储地址
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        return new File(directory + File.separator + fileName);
    }

    /**
     * 文件下载
     * @param downloadUrl 下载路径
     * @param downloadedLength 已下载长度
     * @param file 下载文件存储
     * @return
     */
    private int downloadFile(String downloadUrl,long downloadedLength,File file) {
        ResponseBody responseBody = null;
        RandomAccessFile randomAccessFile = null;
        InputStream is = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes=" + downloadedLength + "-")//跳过已经下载的长度
                    .url(downloadUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                responseBody = response.body();
                is = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file,"rw");
                randomAccessFile.seek(downloadedLength);//跳过已下载的长度（字节）
                byte[] b = new byte[1024];
                int total = 0,len;
                while((len = is.read(b)) != -1) {
                    //暂停
                    if (isPause) {
                        return DownloadConst.TYPE_PAUSE;
                    }
                    //取消
                    if (isCancel) {
                        return DownloadConst.TYPE_CANCEL;
                    }
                    total += len;
                    //将文件写入目录
                    randomAccessFile.write(b,0,len);
                    //更新下载进度
                    long progress = (downloadedLength + total);
                    publishProgress((int) (progress * 100 / totalLength));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return DownloadConst.TYPE_FAILED;
        }finally {
            try {
                if (null != randomAccessFile) {
                    randomAccessFile.close();
                }
                if (null != responseBody) {
                    responseBody.close();
                }
                if (null != is) {
                    is.close();
                }
                //如果取消下载，则需要删除已下载的文件
                if (isCancel && file != null) {
                    file.delete();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DownloadConst.TYPE_SUCCESS;
    }

    /**
     * 获取下载文件总长度
     *
     * @param downloadUrl 下载文件路径
     * @return
     * @throws IOException
     */
    private long getDownloadFileLenth(String downloadUrl) throws IOException {
        long totalLen = 0;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = okHttpClient.newCall(request).execute();
        if (null != response && response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            totalLen = responseBody.contentLength();
            responseBody.close();
        }
        return totalLen;
    }

    @Override
    protected void onProgressUpdate(Integer... progresses) {
        //进度
        int progress = progresses[0];
        if (progress > lastProgress) {
            //更新下载进度
            downloadListener.onProgress(progress);
            this.lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer code) {
        if (null == code) {
            downloadListener.onFailed();
            return;
        }
        switch (code) {
            case DownloadConst.TYPE_CANCEL:
                downloadListener.onCancel();
                break;
            case DownloadConst.TYPE_FAILED:
                downloadListener.onFailed();
                break;
            case DownloadConst.TYPE_SUCCESS:
                downloadListener.onSuccess();
                break;
            case DownloadConst.TYPE_PAUSE:
                downloadListener.onPause((int)lastProgress);
                break;
        }
    }

    /**
     * 取消
     */
    public void isCancel(){
        this.isCancel = true;
    }

    /**
     * 暂停
     */
    public void isPause(){
        this.isPause = true;
    }
}

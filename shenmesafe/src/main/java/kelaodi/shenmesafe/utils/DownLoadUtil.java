/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.utils;

import android.app.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/4/18.
 */
public class DownLoadUtil {
    /**
     * @param servicePath 服务器路径
     * @param savedPath   下载保存的路径
     * @param pd          进度条对话框
     * @return 下载成功，返回文件对象，失败null
     */
    public static File download(String servicePath, String savedPath, ProgressDialog pd) {
        try {
            URL url = new URL(servicePath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            int code = conn.getResponseCode();
            if (code == 200) {
                pd.setMax(conn.getContentLength());
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0;
                File file = new File(savedPath);
                FileOutputStream fos = new FileOutputStream(file);

                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    pd.setProgress(total);
                    Thread.sleep(200);
                }
                fos.flush();
                fos.close();
                is.close();
                return file;
            } else {
                return null;//失败
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取服务器文件的名称
     * * @param servicePath
     * * @return
     */
    public static String getFileName(String servicePath) {
        return servicePath.substring(servicePath.lastIndexOf("/") + 1);
    }


}


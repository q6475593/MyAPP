
/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.constant.constant;
import kelaodi.shenmesafe.domain.UpdateInfo;
import kelaodi.shenmesafe.engine.UpdateInfoParser;
import kelaodi.shenmesafe.ui.TVoffAnimation;
import kelaodi.shenmesafe.ui.TVonAnimation;
import kelaodi.shenmesafe.utils.DownLoadUtil;


public class SplashActivity extends Activity {

    public static final String TAG = "SplashActivity";
    private TextView tv_splash_version = null;
    private UpdateInfo info;
    private Context context = this;
    private View splash;
    private ProgressDialog progressDialog;//下载进度的对话框
    private TVoffAnimation tvoffAnimation = new TVoffAnimation();
    private TVonAnimation tvonAnimation = new TVonAnimation();
    private SharedPreferences sp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case constant.PARSE_XML_ERROR:
                    Toast.makeText(context, "解析失败", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                    break;
                case constant.SERVER_ERROR:
                    Toast.makeText(context, "服务器状态异常", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                    break;
                case constant.URL_ERROR:
                    Toast.makeText(context, "服务器路径错误", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                    break;
                case constant.NETWORK_ERROR:
                    Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                    break;
                case constant.PARSE_XML_SUCCESS:
                    if (getAppversion().equals(info.getVersion())) {
                        Toast.makeText(context, "验证成功", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "版本号相同，进入主界面");
                        loadMainUI();
                    } else {
                        Log.i(TAG, "版本号不相同，弹出来升级提示对话框");
                        showUpdateDialog();
                    }
                    break;
                case constant.DOWNLOAD_SUCCESS:
                    File file = (File) msg.obj;
                    install(file);
                    break;
                case constant.DOWNLOAD_ERROR:
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                    break;
                case constant.ISNOTUPDATE:
                    loadMainUI();
                    break;
            }
        }
    };

    /**
     * 安装apk文件
     */
    private void install(File file) {
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    /**
     * 自动提示对话框，升级的啊。
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("升级提醒");
        builder.setMessage(info.getDescription());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = new ProgressDialog(SplashActivity.this);
                progressDialog.setTitle("升级下载");
                progressDialog.setMessage("正在下载中");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                String apkurl = info.getApkurl();
                final File file = new File(Environment.getExternalStorageDirectory(),
                        DownLoadUtil.getFileName(apkurl));
                //判断sd卡是否可用
                Log.i(TAG + "sd可用么？", Environment.getExternalStorageState());
                if (Environment.getExternalStorageState().
                        equals(Environment.MEDIA_MOUNTED)) {
                    new Thread() {
                        @Override
                        public void run() {
                            File saveFile = DownLoadUtil.download(info.getApkurl(),
                                    file.getAbsolutePath(), progressDialog);
                            super.run();
                            Message msg = Message.obtain();
                            if (saveFile != null) {
                                msg.what = constant.DOWNLOAD_SUCCESS;
                                msg.obj = saveFile;
                            } else {
                                msg.what = constant.DOWNLOAD_ERROR;
                            }
                            progressDialog.dismiss();
                            handler.sendMessage(msg);

                        }
                    }.start();
                } else {
                    Toast.makeText(getApplicationContext(), "sd卡不可用", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadMainUI();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = findViewById(R.id.rl_splash);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本号:" + getAppversion());
        //连接互联网更新版本
        splash.setAnimation(tvonAnimation);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        new Thread(new CheckVersionTask()).start();
    }

    private class CheckVersionTask implements Runnable {
        private Message msg = Message.obtain();

        @Override
        public void run() {
            SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
            boolean Isupdate = sp.getBoolean("update", true);
            if (!Isupdate) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg.what = constant.ISNOTUPDATE;
            } else if (Isupdate) {
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL(getResources().getString(R.string.ServiceName));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        info = UpdateInfoParser.getUpdateInfo(is);
                        if (info != null) {
                            msg.what = constant.PARSE_XML_SUCCESS; //成功
                        } else {
                            msg.what = constant.PARSE_XML_ERROR;//错误
                        }
                    } else {
                        msg.what = constant.SERVER_ERROR; //服务器内部错误
                    }
                } catch (MalformedURLException e) {
                    msg.what = constant.URL_ERROR;//url错误
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = constant.NETWORK_ERROR;//网络错误
                } finally {
                    long endTime = System.currentTimeMillis();
                    long dTime = endTime - startTime;
                    if (dTime < 2000) {
                        try {
                            Thread.sleep(2000 - dTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
            handler.sendMessage(msg);
        }
    }

    /**
     * 进入主界面
     */
    private void loadMainUI() {
        tvoffAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!sp.getBoolean("Issetup", false) ) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("Issetup", true);
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, SetupOneActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        splash.startAnimation(tvoffAnimation);

    }


    /**
     * 获取应用程序的版本号
     *
     * @return
     */

    private String getAppversion() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}

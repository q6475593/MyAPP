
/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kelaodi.shenmesafe.constant.constant;
import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.domain.UpdateInfo;
import kelaodi.shenmesafe.engine.UpdateInfoParser;


public class SplashActivity extends Activity {

    public static final String TAG = "SplashActivity";
    private TextView tv_splash_version = null;
    private UpdateInfo info;
    private Context context = this;
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
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本号:" + getAppversion());
        //连接互联网更新版本
        new Thread(new CheckVersionTask()).start();
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(2000);
        findViewById(R.id.rl_splash).setAnimation(aa);

    }

    private class CheckVersionTask implements Runnable {


        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            Message msg = Message.obtain();
            try {
                URL url = new URL(getResources().getString(R.string.ServiceName));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);

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
                handler.sendMessage(msg);
            }
        }
    }

    /**
     * 进入主界面
     */
    private void loadMainUI() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

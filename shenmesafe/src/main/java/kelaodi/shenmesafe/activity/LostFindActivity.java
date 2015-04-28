package kelaodi.shenmesafe.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/20.
 */
public class LostFindActivity extends Activity {
    private SharedPreferences sp;
    private View lockSIM;
    private ImageView lock;
    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfind);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        initView();
        initDate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        initView();
        initDate();
    }

    private void initDate() {
        String sim = sp.getString("sim", "");
        if (TextUtils.isEmpty(sim)) {
            lock.setImageResource(R.drawable.unlock);
        } else {
            lock.setImageResource(R.drawable.lock);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        lock = (ImageView) findViewById(R.id.lock);
        lockSIM = findViewById(R.id.lockSIM);
        final String simnumber = tm.getSimSerialNumber();
        lockSIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                String sim = sp.getString("sim", "");
                if (TextUtils.isEmpty(sim)) {
                    editor.putString("sim", simnumber);
                    editor.commit();
                    lock.setImageResource(R.drawable.lock);
                } else {
                    editor = sp.edit();
                    editor.putString("sim", "");
                    editor.commit();
                    lock.setImageResource(R.drawable.unlock);
                }
            }
        });
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.menu_change_name:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("更改名称");
                builder.setIcon(R.drawable.notification);
                final EditText et = new EditText(this);
                et.setHint("请输入新的名称");
                builder.setView(et);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newname = et.getText().toString().trim();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("newname", newname);
                        editor.commit();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lostfindmenu, menu);
        return true;
    }
}


/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.ui.SettingView;

/**
 * Created by Administrator on 2015/4/19.
 */
public class SettingActivity extends Activity {
    private SettingView sv_setting_update, sv_setting_Opentest;
    private CheckBox tv_setting_cb, tv_opentext_cb;
    private SharedPreferences sp;
    private boolean Isupdate;
    private boolean Isopentest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initActionbar();
        oncliclistener();
    }

    private void initActionbar() {
    }

    private void initView() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        sv_setting_update = (SettingView) findViewById(R.id.sv_setting_update);
        sv_setting_Opentest = (SettingView) findViewById(R.id.sv_setting_Opentest);
        tv_setting_cb = (CheckBox) sv_setting_update.findViewById(R.id.tv_setting_cb);
        tv_opentext_cb = (CheckBox) sv_setting_Opentest.findViewById(R.id.tv_setting_cb);
        Isupdate = sp.getBoolean("update", true);
        Isopentest = sp.getBoolean("opentest", true);
        tv_setting_cb.setChecked(Isupdate);
        tv_opentext_cb.setChecked(Isopentest);
    }

    private void oncliclistener() {
        final SharedPreferences.Editor editor = sp.edit();
        sv_setting_update.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tv_setting_cb.setPressed(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        tv_setting_cb.setPressed(false);
                        if (sv_setting_update.Ischecked(tv_setting_cb)) {
                            sv_setting_update.Setchecked(tv_setting_cb, false);

                            editor.putBoolean("update", false);
                        } else if (sv_setting_update.Ischecked(tv_setting_cb) == false) {
                            sv_setting_update.Setchecked(tv_setting_cb, true);
                            editor.putBoolean("update", true);
                        }
                        editor.commit();
                        break;
                }
                return true;
            }
        });
        sv_setting_Opentest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tv_opentext_cb.setPressed(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        tv_opentext_cb.setPressed(false);
                        if (sv_setting_Opentest.Ischecked(tv_opentext_cb)) {
                            sv_setting_Opentest.Setchecked(tv_opentext_cb, false);
                            editor.putBoolean("opentest", false);
                        } else if (sv_setting_Opentest.Ischecked(tv_opentext_cb) == false) {
                            sv_setting_Opentest.Setchecked(tv_opentext_cb, true);
                            editor.putBoolean("opentest", true);
                        }
                        editor.commit();
                        break;
                }
                return true;
            }
        });
        Isupdate = sp.getBoolean("update", true);
        Isopentest = sp.getBoolean("opentest", true);
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





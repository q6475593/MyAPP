/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.adapter.HomeAdapter;
import kelaodi.shenmesafe.constant.constant;
import kelaodi.shenmesafe.ui.TVonAnimation;
import kelaodi.shenmesafe.utils.MD5;

import static java.lang.String.valueOf;


/**
 * Created by Administrator on 2015/4/18.
 */
public class HomeActivity extends Activity {
    private GridView gv_home;
    private HomeActivity homeActivity;
    private Context context = this;
    private SharedPreferences sp;
    private LayoutInflater inflate;
    private EditText et_home_password_first, et_home_password_second, et_home_password_only;
    private String password_first, password_second, single_only, existing_password;
    private View doublepassword, singlepassword, linearlayout_home;
    private boolean Isempty, Isopentest;
    private TVonAnimation tVonAnimation=new TVonAnimation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();




    }

    @Override
    protected void onResume() {
        initView();
        super.onResume();
    }

    private void initView() {
        Bundle bd = getIntent().getExtras();
        linearlayout_home = findViewById(R.id.linearlayout_home);
        int fromwhere = bd.getInt("fromwhere");
        if (fromwhere== constant.ONLY){
            linearlayout_home.setAnimation(tVonAnimation);
        }
        gv_home = (GridView) findViewById(R.id.gv_home);
        inflate = LayoutInflater.from(HomeActivity.this);
        sp = context.getSharedPreferences("config", MODE_PRIVATE);
        existing_password = sp.getString("password", "");
        gv_home.setAdapter(new HomeAdapter(homeActivity, context));
        Isempty = TextUtils.isEmpty(existing_password);
        Isopentest = sp.getBoolean("opentest", true);
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        if (Isopentest) {//打开
                            if (!Isempty) {
                                dialogsingle(LostFindActivity.class);
                            } else if (Isempty) {
                                dialogdouble();
                            }
                        } else if (!Isopentest) {
                            intent(LostFindActivity.class);
                        }
                        break;
                    case 8:
                        if (Isopentest) {
                            if (!Isempty) {
                                dialogsingle(SettingActivity.class);
                            } else if (Isempty) {
                                dialogdouble();
                            }
                        } else if (!Isopentest) {
                            intent(SettingActivity.class);
                        }
                        break;
                    case 9:
                        if (Isopentest) {
                            intent(PasswordResetActivity.class);
                        } else if (!Isopentest) {

                        }
                        break;
                }

            }
        });
    }

    private void dialogsingle(final Class<?> cls) {
        final AlertDialog.Builder builder_single = new AlertDialog.Builder(this, R.style.DoublePassword);
        builder_single.setTitle("请输入密码");
        singlepassword = inflate.inflate(R.layout.home_single_password_edittext, null);
        builder_single.setView(singlepassword);
        builder_single.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_home_password_only = (EditText) singlepassword.findViewById(R.id.et_home_password_only);
                single_only = et_home_password_only.getText().toString().trim();
                if (MD5.MD5(single_only).equals(existing_password)) {
                    Toast.makeText(context, "验证成功!", Toast.LENGTH_SHORT).show();
                    intent(cls);
                } else if (!single_only.equals(existing_password)) {
                    Toast.makeText(context, "密码错误，请重新输入!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder_single.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder_single.show();
    }

    private void dialogdouble() {
        final AlertDialog.Builder builder_double = new AlertDialog.Builder(this, R.style.DoublePassword);
        builder_double.setTitle("请设置密码");
        doublepassword = inflate.inflate(R.layout.home_double_password_edittext, null);
        builder_double.setView(doublepassword);
        builder_double.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_home_password_first = (EditText) doublepassword.findViewById(R.id.et_home_password_first);
                et_home_password_second = (EditText) doublepassword.findViewById(R.id.et_home_password_second);
                password_first = et_home_password_first.getText().toString().trim();
                password_second = et_home_password_second.getText().toString().trim();
                SharedPreferences.Editor editor = sp.edit();
                if (TextUtils.isEmpty(password_second) || TextUtils.isEmpty(password_first)) {
                    Toast.makeText(context, "两次密码必须都得填写", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(password_second) && !TextUtils.isEmpty(password_first)) {
                    if (password_first.equals(password_second) && password_first.length() > 5
                            && password_second.length() > 5) {
                        editor.putString("password", MD5.MD5(password_second));
                        editor.commit();
                        Toast.makeText(context, "已成功保存密码，请牢记密码" + password_second, Toast.LENGTH_SHORT).show();
                        existing_password = sp.getString("password", "");
                        Isempty = TextUtils.isEmpty(existing_password);
                        Isopentest = sp.getBoolean("opentest", true);
                    } else if (!password_first.equals(password_second)) {
                        Toast.makeText(context, "您两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    } else if (password_first.length() < 6 || password_second.length() < 6) {
                        Toast.makeText(context, "输入的密码必须大于六位", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder_double.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder_double.show();

    }

    private void intent(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actionbar_menu, menu);
        MenuItem[] mMenuItem = new MenuItem[menu.size()];
        for (int i = 0; i < menu.size(); i++) {
            mMenuItem[i] = menu.getItem(i);
            mMenuItem[i].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_write_note:
                break;
            case R.id.action_togamble:
                break;
            case R.id.action_maps:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

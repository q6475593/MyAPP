/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.adapter.HomeAdapter;


/**
 * Created by Administrator on 2015/4/18.
 */
public class HomeActivity extends Activity {
    private GridView gv_home;
    private HomeActivity homeActivity;
    private Context context = this;
    private SharedPreferences sp;
    private LayoutInflater inflate;
    private EditText et_home_password_first, et_home_password_second;
    private String password_first, password_second, existing_password;
    private View doublepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    private void initView() {
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(homeActivity, context));
        inflate = LayoutInflater.from(HomeActivity.this);


        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        dialog(LostFindActivity.class);
                        break;
                    case 8:
                        dialog(SettingActivity.class);
                        break;
                }
            }
        });
    }

    private void dialog(final Class<?> cls) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DoublePassword);
        builder.setTitle("请设置密码");
        doublepassword = inflate.inflate(R.layout.home_double_password_edittext, null);

        builder.setView(doublepassword);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_home_password_first = (EditText) doublepassword.findViewById(R.id.et_home_password_first);
                et_home_password_second = (EditText) doublepassword.findViewById(R.id.et_home_password_second);
                password_first = et_home_password_first.getText().toString().trim();
                password_second = et_home_password_second.getText().toString().trim();
                sp = context.getSharedPreferences("password", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                existing_password = sp.getString("password", "");
                if (TextUtils.isEmpty(sp.getString("password", ""))) {
                    if (TextUtils.isEmpty(password_second) && TextUtils.isEmpty(password_first)) {
                        Toast.makeText(context, "输入的密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!TextUtils.isEmpty(password_second) && !TextUtils.isEmpty(password_first)) {
                        if (password_first == password_second && password_first.length() > 6
                                && password_second.length() > 6) {
                            editor.putString("password", password_second);
                            editor.commit();
                            Toast.makeText(context, "已成功保存密码，请牢记密码" + password_second, Toast.LENGTH_SHORT).show();
                        } else if (password_first != password_second) {
                            Toast.makeText(context, "您两次输入的密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                            Log.i("草",password_first.toString()+"草"+password_second.toString());
                        } else if (password_first.length() < 6 || password_second.length() < 6) {
                            Toast.makeText(context, "输入的密码必须大于六位", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (!TextUtils.isEmpty(sp.getString("password", ""))) {
                    if (sp.getString("password", "") == existing_password) {
                        Intent intent = new Intent(context, cls);
                        startActivity(intent);
                    } else if (sp.getString("password", "") != existing_password) {
                        Toast.makeText(context, "密码错误，请重新输入!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


}

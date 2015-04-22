/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.activity.HomeActivity;

/**
 * Created by Administrator on 2015/4/19.
 */
public class HomeAdapter extends BaseAdapter {
    private Context context;
    private HomeActivity homeActivity;
    private boolean Isopentest;
    private SharedPreferences sp;

    public HomeAdapter(Activity homeActivity, Context context) {
        this.context = context;
        this.homeActivity = (HomeActivity) homeActivity;

    }

    public static final String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
            "病毒查杀", "系统优化", "高级工具", "设置中心"};
    private static final int[] icons = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
            R.drawable.atools, R.drawable.settings};


    public static final String[] opentestnames = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
            "病毒查杀", "系统优化", "高级工具", "设置中心", "重置密码"};
    private static final int[] opentesticons = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
            R.drawable.atools, R.drawable.settings, R.drawable.password_setting};

    //返回总条目数
    @Override
    public int getCount() {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean Isopentest = sp.getBoolean("opentest", true);
        if (Isopentest) {
            return opentesticons.length;
        } else if (!Isopentest) {
            return icons.length;
        }
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    //返回每个位置对应的view对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean Isopentest = sp.getBoolean("opentest", true);
        View gridview = View.inflate(context, R.layout.grid_home_item, null);
        TextView tv_name = (TextView) gridview.findViewById(R.id.tv_home_name);
        ImageView iv_icon = (ImageView) gridview.findViewById(R.id.iv_home_icon);


        if (Isopentest) {
            tv_name.setText(opentestnames[position]);
            iv_icon.setImageResource(opentesticons[position]);
            Log.i("嘟嘟嘟", "嘟嘟嘟");
        } else if (!Isopentest) {
            tv_name.setText(names[position]);
            iv_icon.setImageResource(icons[position]);
        }


        if (position == 0) {
            SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
            String newname = sp.getString("newname", "");
            if (!TextUtils.isEmpty(newname)) {
                tv_name.setText(newname);
            }
        }
        return gridview;
    }
}

/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.activity.HomeActivity;

/**
 * Created by Administrator on 2015/4/19.
 */
public class HomeAdapter extends BaseAdapter {
    private Context context;
    private HomeActivity homeActivity;

    public HomeAdapter(Activity homeActivity, Context context) {
        this.context = context;
        this.homeActivity = (HomeActivity) homeActivity;

    }

    private static final String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
            "病毒查杀", "系统优化", "高级工具", "设置中心"};
    private static final int[] icons = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
            R.drawable.atools, R.drawable.settings};

    //返回总条目数
    @Override
    public int getCount() {
        return names.length;
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
        View gridview = View.inflate(context, R.layout.grid_home_item, null);
        TextView tv_name = (TextView) gridview.findViewById(R.id.tv_home_name);
        ImageView iv_icon = (ImageView) gridview.findViewById(R.id.iv_home_icon);
        tv_name.setText(names[position]);
        iv_icon.setImageResource(icons[position]);
        return gridview;
    }
}

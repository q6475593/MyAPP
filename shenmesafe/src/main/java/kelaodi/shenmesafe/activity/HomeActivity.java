/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.adapter.HomeAdapter;

/**
 * Created by Administrator on 2015/4/18.
 */
public class HomeActivity extends Activity {
    private GridView gv_home;
    private HomeActivity homeActivity;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(homeActivity, context));

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 8:
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}

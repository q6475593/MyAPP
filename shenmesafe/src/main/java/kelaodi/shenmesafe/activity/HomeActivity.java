/*
 * 版权所有，翻版必究
 * 作者：王玉琨
 */

package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/18.
 */
public class HomeActivity extends Activity {
    private GridView gv_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        gv_home = (GridView) findViewById(R.id.gv_home);
    }
}

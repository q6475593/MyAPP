package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Administrator on 2015/4/23.
 */
public abstract class BaseSetActivity extends Activity {
    protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        initView();
        setupView();

    }

    public abstract void initView();

    public abstract void setupView();

    public abstract void showNext();

    public abstract void showPrevious();
}

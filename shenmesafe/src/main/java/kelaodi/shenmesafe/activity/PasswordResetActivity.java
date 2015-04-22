package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/22.
 */
public class PasswordResetActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_password_reset);
    }
}

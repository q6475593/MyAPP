package kelaodi.shenmesafe.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.ui.SettingView;

/**
 * Created by Administrator on 2015/4/22.
 */
public class PasswordResetActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actionbar_menu, menu);
        MenuItem[] mMenuItem = new MenuItem[menu.size()];
        for (int i = 0; i < menu.size(); i++) {
            mMenuItem[i] = menu.getItem(i);
            mMenuItem[i].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
//        MenuItem mSearchBar=menu.findItem(R.id.searchbar);

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

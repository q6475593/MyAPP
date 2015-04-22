package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/20.
 */
public class LostFindActivity extends Activity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfind);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        initsetup();


    }

    private void initsetup() {
        if (!isSetup()) {
        }
    }

    private boolean isSetup() {
        return sp.getBoolean("setup", false);
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


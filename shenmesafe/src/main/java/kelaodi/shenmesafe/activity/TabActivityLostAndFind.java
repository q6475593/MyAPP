package kelaodi.shenmesafe.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/28.
 */
public class TabActivityLostAndFind extends TabActivity {
    private TabHost m_tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablostandfind);
        m_tabHost = getTabHost();
        inittab();
    }

    private void inittab() {
        TabHost.TabSpec spec;
        spec = m_tabHost.newTabSpec("one").setIndicator("猴子").setContent(new Intent
                (TabActivityLostAndFind.this, LostFindActivity.class));
        m_tabHost.addTab(spec);
        spec = m_tabHost.newTabSpec("two").setIndicator("企鹅").setContent(new Intent
                (TabActivityLostAndFind.this, Lost2.class));
        m_tabHost.addTab(spec);
        spec = m_tabHost.newTabSpec("three").setIndicator("人类").setContent(new Intent
                (TabActivityLostAndFind.this, Lost3.class));
        m_tabHost.addTab(spec);
        spec = m_tabHost.newTabSpec("four").setIndicator("球球").setContent(new Intent
                (TabActivityLostAndFind.this, Lost4.class));
        m_tabHost.addTab(spec);


    }
}
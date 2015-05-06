package kelaodi.shenmesafe.activity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kelaodi.shenmesafe.R;

/**
 * Created by Administrator on 2015/4/28.
 */
public class TabActivityLostAndFind extends TabActivity {
    private TabHost m_tabHost;
    private ViewPager viewPager;
    private SharedPreferences sp;
    private List<View> mlist = new ArrayList<>();
    private Context context = this;
    private View view1, view2, view3, view4;
    private String phone = "";
    private String outsafenumber = "";
    private ProgressBar pb_tablostandfind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablostandfind);
        initview();
        inittab();
        initviewpager();
    }

    private void initview() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        outsafenumber = sp.getString("safenumber", "");
        pb_tablostandfind = (ProgressBar) findViewById(R.id.pb_tablostandfind);
        pb_tablostandfind.setVisibility(View.GONE);
        m_tabHost = getTabHost();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initviewpager() {
        view1 = LayoutInflater.from(this).inflate(R.layout.activity_lostfind, null);
        view2 = LayoutInflater.from(this).inflate(R.layout.activity_contacts, null);
        view3 = LayoutInflater.from(this).inflate(R.layout.activity_setup_three, null);
        view4 = LayoutInflater.from(this).inflate(R.layout.activity_setup_four, null);
        mlist.add(view1);
        mlist.add(view2);
        mlist.add(view3);
        mlist.add(view4);
        viewPager = (ViewPager) findViewById(R.id.itemViewPager);
        Myviewpager myviewpager = new Myviewpager(mlist, context, phone, outsafenumber, sp, pb_tablostandfind);
        viewPager.setAdapter(myviewpager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                m_tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        m_tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                viewPager.setCurrentItem(m_tabHost.getCurrentTab());
            }
        });

    }


    public class Myviewpager extends PagerAdapter {
        private List<View> mlist;
        private Context context;
        private View lockSIM;
        private ImageView lock;
        private TelephonyManager tm;
        private String phone;
        private String outsafenumber;
        private SharedPreferences sp;
        private ProgressBar pb_tablostandfind;
        private Thread mthread;

        public Myviewpager(List<View> mlist, Context context, String phone, String outsafenumber,
                           SharedPreferences sp, ProgressBar pb_tablostandfind) {
            this.mlist = mlist;
            this.context = context;
            this.phone = phone;
            this.outsafenumber = outsafenumber;
            this.sp = sp;
            this.pb_tablostandfind = pb_tablostandfind;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mlist.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {//Log.i("电话回来",phone);
            switch (position) {
                case 1:
                    initView1(view1);
                    break;
                case 2:
                    selectcontact2(view2);
                    break;
            }
            View v = mlist.get(position);
            container.addView(v);
            return v;


        }

        private void initView1(View view1) {
            tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            lock = (ImageView) view1.findViewById(R.id.lock);
            String simlock = sp.getString("sim", "");
            if (TextUtils.isEmpty(simlock)) {
                lock.setImageResource(R.drawable.unlock);
            } else if (!TextUtils.isEmpty(simlock)) {
                lock.setImageResource(R.drawable.lock);
            }
            lockSIM = view1.findViewById(R.id.lockSIM);
            lockSIM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String simnumber = tm.getSimSerialNumber();
                    SharedPreferences.Editor editor = sp.edit();
                    String sim = sp.getString("sim", "");
                    if (TextUtils.isEmpty(sim)) {
                        editor.putString("sim", simnumber);
                        editor.apply();
                        lock.setImageResource(R.drawable.lock);
                    } else if (!TextUtils.isEmpty(sim)) {
                        editor.putString("sim", "");
                        editor.apply();
                        lock.setImageResource(R.drawable.unlock);
                    }
                }
            });
        }

        private void selectcontact2(View view2) {
            TextView selectcontacts = (TextView) view2.findViewById(R.id.selectcontacts);
            final EditText EditText_contacts = (EditText) view2.findViewById(R.id.EditText_contacts);
            Button button_contact = (Button) view2.findViewById(R.id.button_contact);
            selectcontacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pb_tablostandfind.setVisibility(View.VISIBLE);



                    if (mthread==null){
                        mthread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                startActivityForResult(new Intent(TabActivityLostAndFind.this,
                                        SelectContactActivity.class), 0);
                            }
                        });
                        mthread.start();
                        handler.obtainMessage(0);
                        mthread=null;
                    }











                }
            });
            if (phone != null) {
                EditText_contacts.setText(phone);
            }
            if (outsafenumber != null) {
                EditText_contacts.setText(outsafenumber);
            }
            button_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pattern telRegex = Pattern.compile("[1][358]\\d{9}");
                    String safenumber = EditText_contacts.getText().toString().trim();
                    Matcher Matchersafenumber = telRegex.matcher(safenumber);
                    if (safenumber == null) {
                        Toast.makeText(context, safenumber + "安全号码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!Matchersafenumber.matches()) {
                        Toast.makeText(context, safenumber + "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    } else if (Matchersafenumber.matches()) {
                        Toast.makeText(context, safenumber + "您已经设置了安全号码",
                                Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("safenumber", safenumber);
                        editor.apply();
                    }
                }
            });
        }




        private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:

                        pb_tablostandfind.setVisibility(View.VISIBLE);
                }
            }
        };
















    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            phone = data.getStringExtra("phone");
            EditText EditText_contacts = (EditText) view2.findViewById(R.id.EditText_contacts);
            EditText_contacts.setText(phone);
        }pb_tablostandfind.setVisibility(View.GONE);
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
                        editor.apply();
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


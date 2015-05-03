package kelaodi.shenmesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import kelaodi.shenmesafe.R;
import kelaodi.shenmesafe.domain.ContactInfo;
import kelaodi.shenmesafe.engine.ContactInfoProvider;

/**
 * Created by Administrator on 2015/5/3.
 */
public class SelectContactActivity extends Activity {
    private ListView lv_contact;
    private List<ContactInfo> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        infos = ContactInfoProvider.getContactInfo(this);
        lv_contact.setAdapter(new ContactAdapter());
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ContactInfo info = infos.get(position);
                String phone = info.getPhone();
                setResult(0, new Intent().putExtra("phone", phone));   Log.w("电话",phone);
                finish();
            }
        });
    }


    private class ContactAdapter extends BaseAdapter {



        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.list_contact_item, null);
            TextView tvname = (TextView) view.findViewById(R.id.name);
            TextView tvphone = (TextView) view.findViewById(R.id.phone);
            ContactInfo info = infos.get(position);
            tvname.setText(info.getName());
            tvphone.setText(info.getPhone());
            return view;
        }
    }
}

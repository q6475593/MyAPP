package kelaodi.shenmesafe.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kelaodi.shenmesafe.domain.ContactInfo;

/**
 * Created by Administrator on 2015/5/3.
 */
public class ContactInfoProvider {
    public static List<ContactInfo> getContactInfo(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor personCur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        List<ContactInfo> infos = new ArrayList<>();
        while (personCur.moveToNext()) {
            ContactInfo info = new ContactInfo();
            String cname = "";
            String cnum = "";
            String ID;
            ID = personCur.getString(personCur
                    .getColumnIndex(ContactsContract.Contacts._ID));
            cname = personCur.getString(personCur
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int id = Integer.parseInt(ID);
            if (id > 0) {
                Cursor c = resolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + "=" + ID, null, null);
                while (c.moveToNext()) {
                    cnum = c.getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                c.close();
            }
            info.setPhone(cnum);
            info.setName(cname);
            infos.add(info);
            Log.i("info.toString()", info.toString());
        }
        personCur.close();
        return infos;
    }
}



















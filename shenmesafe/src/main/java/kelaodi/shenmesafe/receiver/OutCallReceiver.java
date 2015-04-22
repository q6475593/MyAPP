package kelaodi.shenmesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import kelaodi.shenmesafe.activity.LostFindActivity;

/**
 * Created by Administrator on 2015/4/22.
 */
public class OutCallReceiver extends BroadcastReceiver {

    private static final String TAG = "OutCallReceiver";
    private String number;

    @Override
    public void onReceive(Context context, Intent intent) {
        number = getResultData();
        if (number.equals("18630187945")) {
            Intent precautionsIntent = new Intent(context, LostFindActivity.class);
            precautionsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(precautionsIntent);
            setResultData(null);
        }
    }
}

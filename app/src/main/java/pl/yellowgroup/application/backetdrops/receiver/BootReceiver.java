package pl.yellowgroup.application.backetdrops.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pl.yellowgroup.application.backetdrops.extras.Util;

public class BootReceiver extends BroadcastReceiver {
    public static final String TAG = "VIVZ";
    public BootReceiver() {
        Log.d(TAG, "BootRecivier");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Util.scheduleAlarm(context);
    }
}

package pl.yellowgroup.application.backetdrops;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import pl.yellowgroup.application.backetdrops.adapters.Filter;

/**
 * Created by darek on 17.10.2016.
 */

public class AppBucketDrops extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .name("db_backet_drop.realm")
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    public static void save(Context context, int filterOption) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("filter", filterOption);
        // commit() writes the data synchronously (blocking the thread its called from). It then informs you about the success of the operation.
        // apply() schedules the data to be written asynchronously. It does not inform you about the success of the operation.
        editor.apply();
    }

    public static int load(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int filterOption = pref.getInt("filter", Filter.NONE);
        return filterOption;
    }
}

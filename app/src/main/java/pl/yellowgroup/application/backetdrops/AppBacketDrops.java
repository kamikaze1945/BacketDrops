package pl.yellowgroup.application.backetdrops;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by darek on 17.10.2016.
 */

public class AppBacketDrops extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .name("db_backet_drop.realm")
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

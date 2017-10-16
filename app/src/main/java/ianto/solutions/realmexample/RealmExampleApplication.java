package ianto.solutions.realmexample;

import android.app.Application;

import ianto.solutions.realmexample.util.WebHandler;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebHandler.setupRetrofit(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

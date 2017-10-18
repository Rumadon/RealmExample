package ianto.solutions.realmexample;

import android.app.Application;

import ianto.solutions.realmexample.util.WebHandler;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


public class RealmExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebHandler.setupRetrofit(getApplicationContext());
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Timber.plant(new Timber.DebugTree());
    }
}

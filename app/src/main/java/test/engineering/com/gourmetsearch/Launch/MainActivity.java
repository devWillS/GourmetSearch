package test.engineering.com.gourmetsearch.Launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import test.engineering.com.gourmetsearch.GourmetSearch.GourmetSearchActivity;
import test.engineering.com.gourmetsearch.R;

public class MainActivity extends AppCompatActivity {
    private static final String REALM_DATABASE = "gourmet_search.realm";
    private static final int REALM_DATABASE_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRealm();

        Intent gourmetSearchIntent = new Intent(getApplicationContext(), GourmetSearchActivity.class);
        startActivity(gourmetSearchIntent);
    }
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration  = new RealmConfiguration.Builder()
                .name(REALM_DATABASE)
                .schemaVersion(REALM_DATABASE_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        //Now set this config as the default config for your app
        //This way you can call Realm.getDefaultInstance elsewhere
        Realm.setDefaultConfiguration(configuration);
    }
}

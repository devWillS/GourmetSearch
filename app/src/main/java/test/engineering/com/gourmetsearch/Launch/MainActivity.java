package test.engineering.com.gourmetsearch.Launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.engineering.com.gourmetsearch.GourmetSearch.GourmetSearchActivity;
import test.engineering.com.gourmetsearch.Model.API.APIInterface;
import test.engineering.com.gourmetsearch.Model.API.APIService;
import test.engineering.com.gourmetsearch.Model.Dao.GenreEntityDao;
import test.engineering.com.gourmetsearch.Model.Response.GenreResponse;
import test.engineering.com.gourmetsearch.Model.Response.HotPepperObject;
import test.engineering.com.gourmetsearch.R;

public class MainActivity extends AppCompatActivity {
    private static final String REALM_DATABASE = "gourmet_search.realm";
    private static final int REALM_DATABASE_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRealm();

        getGenreMaster();
    }

    private void getGenreMaster() {
        APIInterface apiInterface = APIService.createService(APIInterface.class);
        Call<HotPepperObject> call = apiInterface.getGenreMaster(
                getString(R.string.hotpepperApikey),
                "json"
        );
        call.enqueue(new Callback<HotPepperObject>() {
            @Override
            public void onResponse(Call<HotPepperObject> call, Response<HotPepperObject> response) {
                if (response.isSuccessful()) {
                    List<GenreResponse> genreResponseList = response.body().getResults().getGenre();
                    GenreEntityDao.getInstance().add(genreResponseList, true);
                    Log.d("GenreMaster", String.valueOf(genreResponseList));

                    Intent gourmetSearchIntent = new Intent(getApplicationContext(), GourmetSearchActivity.class);
                    startActivity(gourmetSearchIntent);
                }
            }

            @Override
            public void onFailure(Call<HotPepperObject> call, Throwable t) {

            }
        });
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

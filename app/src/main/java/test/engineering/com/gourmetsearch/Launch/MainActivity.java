package test.engineering.com.gourmetsearch.Launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import test.engineering.com.gourmetsearch.GourmetSearch.GourmetSearchActivity;
import test.engineering.com.gourmetsearch.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent gourmetSearchIntent = new Intent(getApplicationContext(), GourmetSearchActivity.class);
        startActivity(gourmetSearchIntent);
    }
}

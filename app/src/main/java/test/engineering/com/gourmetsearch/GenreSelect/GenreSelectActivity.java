package test.engineering.com.gourmetsearch.GenreSelect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import test.engineering.com.gourmetsearch.R;

public class GenreSelectActivity extends AppCompatActivity {
    private ImageView backImageView;
    private RecyclerView genreRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_select);

        setupView();
    }

    private void setupView() {
        backImageView = findViewById(R.id.backImageView);
        genreRecyclerView = findViewById(R.id.genreRecyclerView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}

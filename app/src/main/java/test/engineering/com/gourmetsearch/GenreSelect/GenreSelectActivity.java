package test.engineering.com.gourmetsearch.GenreSelect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import test.engineering.com.gourmetsearch.Entities.GenreEntity;
import test.engineering.com.gourmetsearch.GourmetSearch.GourmetSearchActivity;
import test.engineering.com.gourmetsearch.Model.Dao.GenreEntityDao;
import test.engineering.com.gourmetsearch.R;
import test.engineering.com.gourmetsearch.Util.RecyclerView.ClickListener;
import test.engineering.com.gourmetsearch.Util.RecyclerView.DividerItemDecoration;
import test.engineering.com.gourmetsearch.Util.RecyclerView.RecyclerTouchListener;

public class GenreSelectActivity extends AppCompatActivity {
    private ImageView backImageView;
    private RecyclerView genreRecyclerView;

    private List<GenreEntity> genreEntityList;
    private GenreAdapter adapter;

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
                GenreEntity genreEntity = adapter.getSelectedGenre();
                if (genreEntity != null) {
                    Intent intent = new Intent();
                    intent.putExtra(GourmetSearchActivity.GENRE, genreEntity);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        genreEntityList = GenreEntityDao.getInstance().findAll();

        adapter = new GenreAdapter(genreEntityList);
        genreRecyclerView.setAdapter(adapter);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        genreRecyclerView.addItemDecoration(new DividerItemDecoration(1));
        genreRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                genreRecyclerView,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        adapter.setSelectedPosition(position);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                    @Override
                    public void onClickOutOfItem() {

                    }
                }));
    }
}

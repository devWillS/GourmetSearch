package test.engineering.com.gourmetsearch.GenreSelect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.engineering.com.gourmetsearch.R;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder> implements View.OnClickListener {
    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_cell, viewGroup, false);
        inflate.setOnClickListener(this);
        return new GenreViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder genreViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}

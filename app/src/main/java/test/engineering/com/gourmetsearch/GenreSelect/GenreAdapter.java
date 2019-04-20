package test.engineering.com.gourmetsearch.GenreSelect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import test.engineering.com.gourmetsearch.Entities.GenreEntity;
import test.engineering.com.gourmetsearch.R;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder> {
    private List<GenreEntity> genreEntityList;
    private Integer selectedPosition;

    public GenreAdapter(List<GenreEntity> genreEntityList) {
        this.genreEntityList = genreEntityList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_cell, viewGroup, false);
        return new GenreViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder genreViewHolder, int i) {
        genreViewHolder.setGenre(genreEntityList.get(i), selectedPosition != null && selectedPosition == i);
    }

    @Override
    public int getItemCount() {
        return genreEntityList.size();
    }

    public void setSelectedPosition(int selectedPosition) {
        if (this.selectedPosition != null && this.selectedPosition == selectedPosition) {
            this.selectedPosition = null;
        } else {
            this.selectedPosition = selectedPosition;
        }
        notifyDataSetChanged();
    }

    public GenreEntity getSelectedGenre() {
        if (selectedPosition == null) {
            return null;
        }
        return genreEntityList.get(selectedPosition);
    }
}

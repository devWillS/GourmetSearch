package test.engineering.com.gourmetsearch.GenreSelect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import test.engineering.com.gourmetsearch.Entities.GenreEntity;
import test.engineering.com.gourmetsearch.R;

public class GenreViewHolder extends RecyclerView.ViewHolder {
    private TextView genreNameTextView;
    private ImageView checkmarkImageView;

    public GenreViewHolder(@NonNull View itemView) {
        super(itemView);
        genreNameTextView = itemView.findViewById(R.id.genreNameTextView);
        checkmarkImageView = itemView.findViewById(R.id.checkmarkImageView);
    }

    public void setGenre(GenreEntity genre, boolean selected) {
        genreNameTextView.setText(genre.getName());
        if (selected) {
            checkmarkImageView.setVisibility(View.VISIBLE);
        } else {
            checkmarkImageView.setVisibility(View.INVISIBLE);
        }
    }
}

package noh.android.moviemanager.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import noh.android.moviemanager.R;
import noh.android.moviemanager.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    Movie movie;
    @BindView(R.id.ivMovieBackdrop)
    ImageView ivMovieBackdrop;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.tvVotesCount)
    TextView tvVotesCount;
    @BindView(R.id.ivMovieImage)
    ImageView ivMovieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Movie saved as favorite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movie = (Movie) extras.getSerializable("MOVIE");
            this.setTitle(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvVotesCount.setText(movie.getVoteCount() + " Votes");
            tvRating.setText(movie.getVoteAverage() + " /10.0");

            Picasso.with(this).load(movie.getPosterPath()).into(ivMovieImage);
            Picasso.with(this).load(movie.getBackdropPath()).into(ivMovieBackdrop);

        }
    }

    @OnClick(R.id.ivRateThisStar)
    public void onViewClicked() {
    }
}

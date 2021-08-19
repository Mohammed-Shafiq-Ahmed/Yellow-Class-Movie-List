package com.shafiq.yellowclassmovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MovieDetails extends AppCompatActivity {
    private EditText movieEditText, directorEditText;
    private Button deleteButtonl;
    private Movie selectedMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initWidgets();
        checkForEditMovie();
    }

    private void initWidgets() {
        movieEditText = findViewById(R.id.moviename);
        directorEditText=findViewById(R.id.director);
        deleteButtonl=findViewById(R.id.deletedMovieButton);

    }

    private void checkForEditMovie() {
        Intent previousIntent=getIntent();

        int passedMovieID = previousIntent.getIntExtra(Movie.MOVIE_EDIT_EXTRA, -1);
        selectedMovie = Movie.MovieForID(passedMovieID);
        if(selectedMovie != null) {
            movieEditText.setText(selectedMovie.getMovieName());
            directorEditText.setText(selectedMovie.getDirector());
        }
        else {
            deleteButtonl.setVisibility(View.INVISIBLE);
        }
    }



    public void saveMovie(View view) {
        SQLiteManager sqLiteManager=SQLiteManager.instanceOfDatabase(this);
        String movie = String.valueOf(movieEditText.getText());
        String director=String.valueOf(directorEditText.getText());

        if(selectedMovie == null) {
            int id=Movie.movieArrayList.size();
            Movie newMovie=new Movie(id, movie, director);
            Movie.movieArrayList.add(newMovie);
            sqLiteManager.addMovieToDatabase(newMovie);

        }
        else {
            selectedMovie.setMovieName(movie);
            selectedMovie.setDirector(director);
            sqLiteManager.updateMovieInDB(selectedMovie);
        }
        finish();
    }

    public void deleteMovie(View view) {
        selectedMovie.setDeleted(new Date());
        SQLiteManager sqLiteManager=SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateMovieInDB(selectedMovie);
        finish();
    }
}
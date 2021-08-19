package com.shafiq.yellowclassmovielist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MovieListActivity extends AppCompatActivity {
    private ListView movieListView;
    Button btnLogout;
FirebaseAuth firebaseAuth;
GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        btnLogout=findViewById(R.id.btLogout);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            initWidgets();
            loadFromDBToMemory();
            setMovieAdapter();
            setOnClickListener();
            Toast.makeText(this, "Firebase is Connected", Toast.LENGTH_SHORT).show();
        }


        googleSignInClient= GoogleSignIn.getClient(MovieListActivity.this
        , GoogleSignInOptions.DEFAULT_SIGN_IN);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.signOut();
                            Toast.makeText(getApplicationContext(),"Logout Successful", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }
                });

            }
        });
    }


    private void initWidgets() {
        movieListView=findViewById(R.id.MovieListView);

    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager=SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateMovieListArray();
    }

    private void setMovieAdapter() {
MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), Movie.nonDeletedMovies());
movieListView.setAdapter(movieAdapter);
    }
    private void setOnClickListener() {
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Movie selectedMovie =(Movie) movieListView.getItemAtPosition(pos);
                Intent editmovieIntent = new Intent(getApplicationContext(), MovieDetails.class);
                editmovieIntent.putExtra(Movie.MOVIE_EDIT_EXTRA, selectedMovie.getId());
                startActivity(editmovieIntent);

            }
        });
    }


    public void newMovie(View view) {
        Intent newMovieIntent=new Intent(this,MovieDetails.class);
        startActivity(newMovieIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMovieAdapter();
    }

}
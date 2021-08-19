package com.shafiq.yellowclassmovielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie>
{
public MovieAdapter(Context context, List<Movie> movies) {

    super(context, 0, movies);
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Movie movie=getItem(position);
    if(convertView==null)
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.movie_cell, parent, false);

        TextView name = convertView.findViewById(R.id.cellName);
        TextView director = convertView.findViewById(R.id.cellDirector);
        name.setText(movie.getMovieName());
        director.setText(movie.getDirector());
    return convertView;
    }
}
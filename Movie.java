package com.shafiq.yellowclassmovielist;

import java.util.ArrayList;
import java.util.Date;

public class Movie {
    public static ArrayList<Movie> movieArrayList=new ArrayList<>();
    public static String MOVIE_EDIT_EXTRA = "movieEdit";
    private int id;
    private String movieName;
    private String director;
    private Date deleted;

    public Movie(int id, String movieName, String director, Date deleted) {
        this.id = id;
        this.movieName = movieName;
        this.director = director;
        this.deleted = deleted;
    }

    public Movie(int id, String movieName, String director) {
        this.id = id;
        this.movieName = movieName;
        this.director = director;
        deleted=null;
    }

    public static Movie MovieForID(int passedMovieID) {
        for(Movie movie : movieArrayList) {
            if(movie.getId() == passedMovieID)
                return movie;
        }
        return null;
    }
    public static ArrayList<Movie> nonDeletedMovies() {
        ArrayList<Movie> nonDeleted=new ArrayList<>();
        for(Movie movie: movieArrayList) {
            if(movie.getDeleted()==null)
                nonDeleted.add(movie);
        }
        return nonDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}

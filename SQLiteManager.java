package com.shafiq.yellowclassmovielist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "MovieDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Movie";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String DIRECTOR_FIELD = "director";
    private static final String DELETED_FIELD = "deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

//ITS WORKING PARTIALLY

    public SQLiteManager(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static SQLiteManager instanceOfDatabase(Context context) {
        if(sqLiteManager == null)
            sqLiteManager=new SQLiteManager(context);
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(DIRECTOR_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
    public void addMovieToDatabase(Movie movie) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(ID_FIELD, movie.getId());
        contentValues.put(NAME_FIELD, movie.getMovieName());
        contentValues.put(DIRECTOR_FIELD, movie.getDirector());
        contentValues.put(DELETED_FIELD, getStringFromDate(movie.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);


    }
    public void populateMovieListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                while(result.moveToNext()) {
                    int id=result.getInt(1);
                    String name = result.getString(2);
                    String director = result.getString(3);
                    String stringDeleted = result.getString(4);
                    Date deleted = getDateFromString(stringDeleted);
                    Movie movie = new Movie(id,name,director,deleted);
                    Movie.movieArrayList.add(movie);
                }
            }
        }
    }

    public void updateMovieInDB(Movie movie) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID_FIELD, movie.getId());
        contentValues.put(NAME_FIELD, movie.getMovieName());
        contentValues.put(DIRECTOR_FIELD, movie.getDirector());
        contentValues.put(DELETED_FIELD, getStringFromDate(movie.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(movie.getId())});


    }
    private String getStringFromDate(Date date) {
        if(date==null)
            return null;
        return dateFormat.format(date);

    }
    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}

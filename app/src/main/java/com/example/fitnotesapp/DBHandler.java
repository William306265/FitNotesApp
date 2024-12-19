package com.example.fitnotesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {


    private static final String DB_NAME = "fitnotes";


    private static final int DB_VERSION = 1;


    private static final String TABLE_NAME = "exercises";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String WEIGHT_COL = "weight";

    private static final String SETS_COL = "sets";

    private static final String REPS_COL = "reps";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + WEIGHT_COL + " INT,"
                + SETS_COL + " INT,"
                + REPS_COL + " INT)";


        db.execSQL(query);
    }

    public void addNewExercise(String exerciseName, Double exerciseWeight, Integer exerciseSets, Integer exerciseReps) {


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();


        values.put(NAME_COL, exerciseName);
        values.put(WEIGHT_COL, exerciseWeight);
        values.put(SETS_COL, exerciseSets);
        values.put(REPS_COL, exerciseReps);


        db.insert(TABLE_NAME, null, values);


        db.close();
    }

    public List<ExerciseModel> getAllExercises() {
        List<ExerciseModel> exerciseList = new ArrayList<>();

        // Get readable database.
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to fetch all records.
        String query = "SELECT * FROM " + TABLE_NAME;

        // Execute the query and get a cursor to iterate over results.
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the results and add them to the list.
        if (cursor.moveToFirst()) {
            do {
                ExerciseModel exercise = new ExerciseModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT_COL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(SETS_COL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(REPS_COL))
                );
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database.
        cursor.close();
        db.close();

        return exerciseList;
    }

    public void deleteExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("exercises", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void deleteAllExercises() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM exercises"); // Replace "exercises" with your actual table name
        db.close();
    }



}
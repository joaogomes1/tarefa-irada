package com.example.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.agenda.model.Task;

public class Database extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "db_agenda";
    private static final String DB_TABLE = "agenda";
    private static final String DONE_COLUMN = "done";
    private static final String DESC_COLUMN = "description";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table
        String query_table = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + " ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DONE_COLUMN + " INTEGER DEFAULT 0, "
                + DESC_COLUMN + " TEXT NOT NULL );";
        db.execSQL(query_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public Task[] getTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        // query
        Cursor cursor = db.query(
                DB_TABLE, new String[]{"id", DONE_COLUMN, DESC_COLUMN},
                null, null, null, null, null
        );
        // put values in classes
        if (cursor != null) {
            // iteration count
            int count = 0;
            // total rows in table
            int total = cursor.getCount();
            // create array
            Task[] tasks = new Task[total];
            // move to first row
            cursor.moveToFirst();

            do {
                // put select results in a class each
                tasks[count] = new Task(
                        Integer.parseInt(cursor.getString(0)), // id
                        Integer.parseInt(cursor.getString(1)), // done
                        cursor.getString(2)                    // description
                );
                count++;
            } while (cursor.moveToNext());
            // closes cursor
            cursor.close();
            // return values
            return tasks;
        }
        return null;
    }

    public void addTask(String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        // insert values to query
        ContentValues values = new ContentValues();
        values.put(DESC_COLUMN, description);
        // insert new task
        db.insert(DB_TABLE, null, values);
        // closes database
        db.close();
    }

    public void updateDone(int id, int done) {
        SQLiteDatabase db = this.getWritableDatabase();
        // insert values to query
        ContentValues values = new ContentValues();
        values.put(DONE_COLUMN, done);
        // update done
        db.update(DB_TABLE, values, "id = ?", new String[]{String.valueOf(id)});
        // closes database
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete task
        db.delete(DB_TABLE, "id = ?", new String[]{String.valueOf(id)});
        // closes database
        db.close();
    }
}

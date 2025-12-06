package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_application_database";
    private static final String TABLE_TASK = "tbl_tasks";
    private static final String TAG = "SQLiteHelper";
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_TASK + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, completed BOOLEAN, create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
        } catch (SQLiteException e){
            Log.e(TAG, "onCreate: " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addTask(Task task) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", task.getTitle());
            contentValues.put("completed", task.isCompleted());
            return database.insert(TABLE_TASK, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "addTask: " + e);
            return -1;
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public List<Task> getTasks() {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = getReadableDatabase();
            cursor = database.rawQuery("SELECT * FROM " + TABLE_TASK + " ORDER BY completed ASC, create_date DESC", null);
            List<Task> tasks = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    task.setId(cursor.getLong(0));
                    task.setTitle(cursor.getString(1));
                    task.setCompleted(cursor.getInt(2) == 1);
                    tasks.add(task);
                } while (cursor.moveToNext());
            }
            database.close();
            return tasks;
        } catch (SQLiteException e) {
            Log.e(TAG, "getTasks: " + e);
            return null;
        } finally {
            if(cursor != null) cursor.close();
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public int updateTask(Task task) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", task.getTitle());
            contentValues.put("completed", task.isCompleted());
            return database.update(TABLE_TASK, contentValues, "id = ?", new String[]{String.valueOf(task.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "updateTask: " + e);
            return -1;
        } finally {
            if(database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public int deleteTask(Task task) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.delete(TABLE_TASK, "id = ?", new String[]{String.valueOf(task.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "deleteTask: " + e);
            return -1;
        } finally {
            if(database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public void deleteAllTasks() {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            database.delete(TABLE_TASK, null, null);
        } catch (SQLiteException e) {
            Log.e(TAG, "deleteAllTasks: " + e);
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public List<Task> searchInTasks(String query) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = getReadableDatabase();
            // This way, SQLite itself escapes the value and SQL Injection becomes zero
            cursor = database.rawQuery("SELECT * FROM " + TABLE_TASK + " WHERE title LIKE ? ORDER BY completed ASC, create_date DESC",
                    new String[]{"%" + query + "%"});
            List<Task> tasks = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    task.setId(cursor.getLong(0));
                    task.setTitle(cursor.getString(1));
                    task.setCompleted(cursor.getInt(2) == 1);
                    tasks.add(task);
                } while (cursor.moveToNext());
            }
            return tasks;
        } catch (SQLiteException e) {
            Log.e(TAG, "searchInTasks: " + e);
            return null;
        } finally {
            if (cursor != null) cursor.close();
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

}

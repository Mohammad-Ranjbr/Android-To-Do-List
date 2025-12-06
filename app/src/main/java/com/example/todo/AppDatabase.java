package com.example.todo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//What tables does this database contain? → entities
//What is the database version? → version
//Should I save the schema somewhere? → exportSchema
//When you use Room, it generates an actual class that inherits from this class.
//You just need to define an Interface (like TaskDao) and Room will generate the necessary code at build time.
@Database(version = 1, exportSchema = false, entities = {Task.class})
public abstract class AppDatabase extends RoomDatabase {

    //We should only have one database open. (Singleton Pattern)
    //SQLite If multiple databases are opened → Crash, Deadlock, Leak.
    //The database is created only once and the entire application uses the same instance
    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "todo_application_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    //Room provides a real implementation of TaskDao so we can work with the database.
    public abstract TaskDao getTaskDao();

}

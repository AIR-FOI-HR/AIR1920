package com.example.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Store.class, Discount.class},
        views = {DiscountDetails.class}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public static final String NAME = "main";
    public static final int VERSION = 1;

    private static MyDatabase INSTANCE = null;

    public synchronized static MyDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    MyDatabase.NAME
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract DAO getDAO();
}

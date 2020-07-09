package com.example.customalarms;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Alarms.class, Math.class}, version = 1)
public abstract class AlarmsDB extends RoomDatabase {
    public abstract AlarmsDao alarmsDao();
    public abstract MathDao mathDao();
}

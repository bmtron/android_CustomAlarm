package com.example.customalarms;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AlarmsDao {
    @Query("SELECT * FROM alarms")
    List<Alarms> getAll();

    @Query("SELECT * FROM alarms WHERE recid = (:id)")
    Alarms loadByRecId(int id);

    @Query("UPDATE alarms SET isON = (:value) where recid = (:id)")
    void updateIsON(int value, int id);

    @Query("UPDATE alarms SET time = (:time) where recid = (:id)")
    void updateTime(String time, int id);

    @Insert
    void insert(Alarms alarm);

    @Query("DELETE FROM Alarms")
    void nukeAlarms();

}

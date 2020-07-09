package com.example.customalarms;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MathDao {

    @Query("SELECT * FROM math")
    List<Math> getAllMath();

    @Query("SELECT recid FROM Math order by recid desc limit 1")
    int getMaxRecId();

    @Query("SELECT * FROM math where recid = (:id)")
    Math getRandomByRecId(int id);

}

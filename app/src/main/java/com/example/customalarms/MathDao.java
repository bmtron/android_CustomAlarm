package com.example.customalarms;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MathDao {

    @Query("SELECT * FROM math")
    List<Math> getAllMath();

    @Query("SELECT * FROM math where recid = (:id)")
    Math getRandomByRecId(int id);

}

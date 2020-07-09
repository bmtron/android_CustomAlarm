package com.example.customalarms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Math {
    @PrimaryKey
    public Integer recID;

    @ColumnInfo(name = "Questions")
    public String Questions;

    @ColumnInfo(name = "Answers")
    public String Answers;

    @ColumnInfo(name = "Difficulty")
    public Integer Difficulty;
}

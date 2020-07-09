package com.example.customalarms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarms {
    @PrimaryKey
    public Integer recID;

    @ColumnInfo(name = "Time")
    public String Time;

    @ColumnInfo(name = "isOn")
    public Integer isOn;
}

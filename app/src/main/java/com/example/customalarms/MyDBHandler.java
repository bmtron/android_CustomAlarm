package com.example.customalarms;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLInput;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarms_data.db";
    public static final String TABLE_NAME = "Alarms";
    public static final String COLUMN_ID = "recID";
    public static final String COLUMN_TIME = "Time";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_TIME + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }
        @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){}

    public ArrayList<ArrayList<String>> loadHandler() {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> temp = new ArrayList<String>();

                    String result_recid = c.getString(0);
                    String result_text = c.getString(1);
                    temp.add(result_recid);
                    temp.add(result_text);
                    result.add(temp);
                } while (c.moveToNext());
            }
        }
        c.close();
        c = null;
        return result;
    }

    public void addHandler(String alarmTime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, alarmTime);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String loadByRecID(String recid) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE recid = " + recid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        String result = null;

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String result_0 = c.getString(1);
                    result = result_0;
                } while(c.moveToNext());
            }
        }
        c.close();
        c = null;
        return result;
    }
}

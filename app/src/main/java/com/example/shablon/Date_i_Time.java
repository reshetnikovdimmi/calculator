package com.example.shablon;

import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;

public class Date_i_Time {
     DB1 adapter;

    public ArrayList<String> getContacts(){
        Cursor cursor = adapter.getAllData();
        cursor.moveToFirst();
        ArrayList<String> nameS = new ArrayList<>();
        while(!cursor.isAfterLast()) {
            nameS.add(cursor.getString(cursor.getColumnIndex("date_3")));
            cursor.moveToNext();
        }
        cursor.close();

        return nameS;
    }
}

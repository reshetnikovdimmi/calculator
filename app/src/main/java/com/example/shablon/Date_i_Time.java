package com.example.shablon;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Date_i_Time  {


    ArrayList<String> names;
    ArrayList<String> names2;
    ArrayList<String> names3;
       Cursor cursor;
    User user;
    Context mContext;
    DB1  adapter;

     public void SummDol (int ID2) {



        // adapter = new DB1(this);
         //adapter.open();
         long userId = ID2;
         //Log.d("userId", String.valueOf(ID2));
        // adapter.open();
         cursor = adapter.getAllData2((int) userId);
    //startManagingCursor(cursor);
            cursor.moveToFirst();
    //расчитываем сумму зад и период
            while (!cursor.isAfterLast()) {
          names2.add(cursor.getString(cursor.getColumnIndex("summ2")));
        cursor.moveToNext();
    }

 user  = adapter.getUser(userId);
            names3.add(String.valueOf(user.getSumm()));
            for (int i = 0; i < names.size() - 1; i++) {
        int s = Integer.parseInt(names3.get(i));
        int s1 = Integer.parseInt(names2.get(i));
        names3.add(String.valueOf(s - s1));
    }
            int summD1 = Integer.parseInt(names3.get(names3.size()-1));
        // user = new User( ID2, summD1);
            //adapter.addRec2(user);
         // SummD1.setText(names3.get(names3.size()-1));
         user.setSummD1(summD1);
         adapter.update(user);
    }
public String DateS (){
    int den, mes, god;
    final String[] date = {null};
    final Calendar c = Calendar.getInstance();
    den = c.get(Calendar.DAY_OF_MONTH);
    mes = c.get(Calendar.MONTH);
    god = c.get(Calendar.YEAR);
    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date1 = year + "." + (month + 1) + "." + dayOfMonth;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
            Date datenorm;

            try {
                datenorm = dateFormat.parse(date1);
                date[0] = dateFormat.format(datenorm.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
            , god, mes, den);

    datePickerDialog.show();
    return date[0];
}
}

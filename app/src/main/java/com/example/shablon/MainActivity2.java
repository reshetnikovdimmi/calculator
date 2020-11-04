package com.example.shablon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class MainActivity2 extends AppCompatActivity {


    Button  button2;
    int cou = 0;
    int cou1 = 0;
    int cou2 = 0;
    int count3 = 0;
    int count4 = 0;
    Date date11, date22;
    long milliseconds;
    private DB1 adapter;
    Cursor cursor;
    int days ;
    long userId;
    ArrayList<String> nameS;
    ArrayList<String> names;
    ArrayList<String> names1;
    ArrayList<String> arr;
    ArrayList<String> names2;
    ArrayList<String> names3;
    ArrayList<String> names6;
    ArrayList<String> stringList4;
LinearLayout tables;
    SimpleDateFormat dateFormat;
    ArrayList<Date> date;
    TextView SummD1,KolD1,SummProz1,VD;
    User user;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
          ActionBar actionBar = getSupportActionBar();
          actionBar.setHomeButtonEnabled(true);
          actionBar.setDisplayHomeAsUpEnabled(true);

          SummD1 = (TextView) findViewById(R.id.SummD1);
          KolD1 = (TextView) findViewById(R.id.KolD1);
          SummProz1 = (TextView) findViewById(R.id.SummProz1);
          VD = (TextView) findViewById(R.id.VD1);
        tables = (LinearLayout) findViewById(R.id.table);

        button2 = (Button) findViewById(R.id.button2);
        adapter = new DB1(this);
        names = new ArrayList<>();
        names1 = new ArrayList<>();
        names2 = new ArrayList<>();
        names3 = new ArrayList<>();
        names6 = new ArrayList<>();
        arr = new ArrayList<>();
        stringList4 = new ArrayList<>();

           dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);

        Bundle c = this.getIntent().getExtras();
        if (c != null) {
            userId = c.getInt("userId");
        }
        adapter.open();

        Log.d("array", String.valueOf(userId));
        user = adapter.getUser(userId);
        cursor = adapter.getAllData2((int) userId);
        startManagingCursor(cursor);

        TreeMap<Date, String > map = new TreeMap<>();
        try {
            map.put (dateFormat.parse("1992.01.01"),"8.25");
            map.put(dateFormat.parse("2015.06.01"),"11.80");
            map.put(dateFormat.parse("2015.06.15"),"11.70");
            map.put(dateFormat.parse("2015.07.15"),"10.74");
            map.put(dateFormat.parse("2015.08.17"),"10.51");
            map.put(dateFormat.parse("2015.09.15"),"9.91");
            map.put(dateFormat.parse("2015.10.15"),"9.49");
            map.put(dateFormat.parse("2015.11.17"),"9.39");
            map.put(dateFormat.parse("2015.12.15"),"7.32");
            map.put(dateFormat.parse("2016.01.25"),"7.94");
            map.put(dateFormat.parse("2016.02.19"),"8.96");
            map.put(dateFormat.parse("2016.03.17"),"8.64");
            map.put(dateFormat.parse("2016.04.15"),"8.14");
            map.put(dateFormat.parse("2016.05.19"),"7.90");
            map.put(dateFormat.parse("2016.06.16"),"8.24");
            map.put(dateFormat.parse("2016.07.15"),"7.52");
            map.put(dateFormat.parse("2016.08.01"),"10.50");
            map.put(dateFormat.parse("2016.09.19"),"10");
            map.put(dateFormat.parse("2017.03.27"),"9.75");
            map.put(dateFormat.parse("2017.05.02"),"9.25");
            map.put(dateFormat.parse("2017.06.19"),"9");
            map.put(dateFormat.parse("2017.09.18"),"8.50");
            map.put(dateFormat.parse("2017.10.30"),"8.25");
            map.put(dateFormat.parse("2017.12.18"),"7.75");
            map.put(dateFormat.parse("2018.02.02"),"7.50");
            map.put(dateFormat.parse("2018.03.26"),"7.25");
            map.put(dateFormat.parse("2018.09.17"),"7.50");
            map.put(dateFormat.parse("2018.12.17"),"7.75");
            map.put(dateFormat.parse("2019.06.17"),"7.50");
            map.put(dateFormat.parse("2019.07.09"),"7.25");
            map.put(dateFormat.parse("2019.09.09"),"7");
            map.put(dateFormat.parse("2019.10.08"),"6.50");
            map.put(dateFormat.parse("2019.12.16"),"6.25");
            map.put(dateFormat.parse("2020.02.10"),"6");
            map.put(dateFormat.parse("2020.04.27"),"5.50");
            map.put(dateFormat.parse("2020.06.22"),"4.50");
            map.put(dateFormat.parse("2020.07.27"),"4.25");
            map.put(dateFormat.parse("2999.12.31"),"4.25");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        date = new ArrayList<>();
        nameS = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            nameS.add(cursor.getString(cursor.getColumnIndex("date_3")));
            names2.add(cursor.getString(cursor.getColumnIndex("summ2")));
            cursor.moveToNext();
        }

nameS.add(0,user.getDate_1());
      nameS.add( user.getDate_2());
        Log.d("cou111", String.valueOf(nameS));
        for (String dateString : nameS) {
            try {
                date.add(dateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < date.size(); i++) {
            Map.Entry<Date, String> value = map.lowerEntry(date.get(i));
                if (map.containsKey(date.get(i)) == false) {
                 map.put(date.get(i), value.getValue());
                }
            }
        Iterator<Map.Entry<Date, String >> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Date, String> entry = iter.next();
            try {
                if (entry.getKey().before(dateFormat.parse(user.getDate_1()))) {
                    iter.remove();
                }
                if (entry.getKey().after(dateFormat.parse(user.getDate_2()))) {
                    iter.remove();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Log.d("array", String.valueOf(user.getCheckBox()));
        if (user.getCheckBox() > 0) {
            names6 = new ArrayList<>(map.values());
            names6.add(0,"1");
            Log.d("cou1", String.valueOf(names6));
                for (Date dates : map.keySet()) {
                    String curr_date = dateFormat.format(dates.getTime());
                names.add(curr_date);
                    names1.add(curr_date);
            }
                //надо удалть
            names.remove(names.size()-1);
            names1.remove(0);
//расчитывае сумму долга
            names3.add(String.valueOf(user.getSumm()));
            Log.d("cou111", String.valueOf(names));
            int c1 =0;
            for (int j = 0; j <names.size()-1; j++) {
               for (int i = 0; i < nameS.size()-2; i++) {
                        if (names.get(j).equals(nameS.get(i))) {
                            Log.d("cou1", String.valueOf(c1));
                            int s = Integer.parseInt(names3.get(names3.size()-1));
                            int s1 = Integer.parseInt(names2.get(i));
                            names3.add(String.valueOf(s-s1));
                            c1++;
                        }else {
                            c1=0;
                        }
                                   }
                    if (c1==0 && names3.get(names3.size()-1).equals(names3.get(j))){
                        names3.add(names3.get(names3.size()-1));
                        c1=0;
                    }
              }
            SummD1.setText(names3.get(names3.size()-1));
        }else {
            cursor = adapter.getAllData2((int) userId);
            startManagingCursor(cursor);
            cursor.moveToFirst();
            //расчитываем сумму зад и период
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(cursor.getColumnIndex("date_3")));
                names1.add(cursor.getString(cursor.getColumnIndex("date_3")));
                names2.add(cursor.getString(cursor.getColumnIndex("summ2")));
                cursor.moveToNext();
            }
            Log.d("array", String.valueOf(names1));
            //cursor.close();
            names.add(0, user.getDate_1());
            names1.add(user.getDate_2());
            names3.add(String.valueOf(user.getSumm()));
            for (int i = 0; i < names.size() - 1; i++) {
                int s = Integer.parseInt(names3.get(i));
                int s1 = Integer.parseInt(names2.get(i));
                names3.add(String.valueOf(s - s1));
            }
            SummD1.setText(names3.get(names3.size()-1));
            for (int i = 0; i<names3.size()+1;i++){
                names6.add(String.valueOf(user.getProz()));
            }
        }
        //асчитываем количество дней м/д датами и заполняем arr
            for (int i = 0; i < names.size(); i++) {
                try {
                    date11 = dateFormat.parse(names.get(i));
                    date22 = dateFormat.parse(names1.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                milliseconds = date22.getTime() - date11.getTime();
                days = (int) (milliseconds / (24 * 60 * 60 * 1000));
                arr.add(String.valueOf(days));
            }
          try {
          date11 = dateFormat.parse(names.get(0));
          date22 = dateFormat.parse(names1.get(names1.size()-1));

          milliseconds = date22.getTime() - date11.getTime();
          days = (int) (milliseconds / (24 * 60 * 60 * 1000));
KolD1.setText(String.valueOf(days));
          } catch (ParseException e) {
              e.printStackTrace();
          }
        //асчитываем процент и заполняем stringList4
        double sum = 0;
        for (int i = 0; i < names.size(); i++) {
            int s = Integer.parseInt(arr.get(i));
            int s1 = Integer.parseInt(names3.get(i));
            Double s4 = (Double) (s * s1 * (Double.parseDouble(names6.get(i+1))) / 365/100);
            Log.d("индекс",arr.get(0) );
            Log.d("индекс",names3.get(0) );
            Log.d("индекс",names6.get(1) );
            stringList4.add(String.valueOf(String.format("%.2f",s4)));
            sum = sum + s4;
            SummProz1.setText(String.valueOf(String.format("%.2f",sum)));
            VD.setText(String.valueOf(user.getSumm()));
        }


        String[] column = {"п/п","Период просрочки", "Кол-во дней", "Сумма зад-ти",  "Проценты руб.","проц"};
        int rl=names.size();
        int cl=column.length;

        ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = createTableLayout(column,rl+1, cl);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        hsv.addView(tableLayout);
       sv.addView(hsv);
        tables.addView(sv);
    }
    private TableLayout createTableLayout(String[] cv, int rowCount, int columnCount)
    {
        // 1) Создаем макет таблицы и ее параметры
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setBackgroundColor(Color.TRANSPARENT);
        // 2) создание tableRow параметры
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(7, 7, 7, 7);
        tableRowParams.weight = 20;
        for (int i = 0; i < rowCount; i++)
        {
            // 3) создание  tableRow
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.TRANSPARENT);
            for (int j= 0; j < columnCount; j++)
            {
                // 4) создание  textView
                TextView textView = new TextView(this);
                textView.setBackgroundColor(Color.GRAY);
                textView.setGravity(Gravity.CENTER);
textView.setTextSize(12);
                int textColor = ContextCompat.getColor(this, R.color.textViewColor);
                int textColor1 = ContextCompat.getColor(this, R.color.colorAccent);
textView.setTextColor(textColor);
                String s1 = Integer.toString(i);
                int id = Integer.parseInt(s1);
                Log.d("TAG", "-___>"+id);
                if (i>=1 && j==1)
                {
                    textView.setText(names.get(cou++) + " - " + names1.get(cou1++))  ;
                }
                if (i>=1 && j==2)
                {Log.d("TAAG", "Set Row cou2");
                    textView.setText(arr.get(cou2++));
                }
                if (i>=1 && j==3)
                {Log.d("TAAG", "Set Row count3");
                   textView.setText(names3.get(count3++));
                }
                if (i>=1 && j==4)
                {Log.d("TAAG", "Set Row count4");
                    textView.setText(stringList4.get(count4++));
                }
                if (i>=1 && j==5)
                {Log.d("TAAG", "Set Row count4");
                   textView.setText(names6.get(i));
                }
                 if (i>=1 && j==0)
                {
                    textView.setText(""+id);
                }
                else if(i==0 && j >=0)
                {
                    Log.d("TAAG", "set Column Headers");
                   textView.setText(cv[j]);
                    textView.setTextColor(textColor1);
                    textView.setTextSize(14);
                 }
                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }
            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }
        return tableLayout;
    }
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == button2) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", userId);
            Log.d("POL1", String.valueOf(userId));
            startActivity(intent);
        }
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity3.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
      }
}

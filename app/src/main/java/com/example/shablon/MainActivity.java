package com.example.shablon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CM_DELETE_ID = 1;
    ListView lvData2;
    LinearLayout linear;
    EditText etName, editText, textView13,prView;
    Button btnCreate, button;
    int den, mes, god, checkBox;
    TextView textDate1, textDate2, SummD1, textView1ID,textView5;
    Cursor cursor;
    private DB1 adapter;
    DB1 db;
    long userId = 0;
    SimpleCursorAdapter scAdapter1;
    CheckBox checkBox1,ProizPr;
    Context mContext;
    User user;
    String date_3;
    String date = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        prView = (EditText) findViewById(R.id.prView);
        button = (Button) findViewById(R.id.button);
        textDate1 = (TextView) findViewById(R.id.textDate1);
        textDate2 = (TextView) findViewById(R.id.textDate2);
        SummD1 = (TextView) findViewById(R.id.SummD1);
        textView1ID = (TextView) findViewById(R.id.textView1ID);
        textView5 = (TextView) findViewById(R.id.textView5);
        //etName = (EditText) findViewById(R.id.etName);
        textView13 = (EditText) findViewById(R.id.textView13);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        linear = (LinearLayout) findViewById(R.id.linear);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox);
        ProizPr = (CheckBox) findViewById(R.id.ProizPr);
        textDate1.setOnClickListener(this);
        textDate2.setOnClickListener(this);
        button.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        adapter = new DB1(this);
        //db = new DB1(this);


        adapter.open();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
            Log.d("POL1", String.valueOf(userId));
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд

            Log.d("POL", String.valueOf(userId));
            //adapter.open();
            user = adapter.getUser(userId);

            textView13.setText(user.getName());
            editText.setText(String.valueOf(user.getSumm()));
            textDate1.setText(user.getDate_1());
            textDate2.setText(user.getDate_2());
            prView.setText(String.valueOf(user.getProz()));

        }


        checkBox = user.getCheckBox();

            boolean checkBox2 = user.getCheckBox() > 0;
        Log.d("array", String.valueOf(user.getCheckBox()));
            checkBox1.setChecked(checkBox2);
        checkBox2 = user.getCheckBox() == 0;
        ProizPr.setChecked(checkBox2);

            if (checkBox == 1){

                prView.setVisibility(View.INVISIBLE);
                textView5.setVisibility(View.INVISIBLE);
            }
            else{

                prView.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);
            }

        checkBox1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked


                if (checked){
                    // Your code
                    checkBox =1;
                    //Toast.makeText(getApplicationContext(), "Еще в разработке", Toast.LENGTH_SHORT).show();
                    ProizPr.setChecked(false);
                    prView.setVisibility(View.INVISIBLE);
                    textView5.setVisibility(View.INVISIBLE);
                }

                else{

                    ProizPr.setChecked(true);
                    checkBox=0;
                    prView.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                }

            }
        });
        ProizPr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked


                if (checked){
                    // Your code
                    checkBox =0;
                    //Toast.makeText(getApplicationContext(), "Еще в разработке", Toast.LENGTH_SHORT).show();
                    checkBox1.setChecked(false);
                    prView.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                }

                else{

                    checkBox1.setChecked(true);
                    checkBox=1;
                    prView.setVisibility(View.INVISIBLE);
                    textView5.setVisibility(View.INVISIBLE);
                }

            }
        });
        Log.d("array", String.valueOf(user.getCheckBox()));
        cursor = adapter.getAllData2((int) userId);
        startManagingCursor(cursor);


        String[] from1 = new String[] {DB1.COLUMN_DATE2, DB1.COLUMN_TXT2};
        int[] to1 = new int[] { R.id.editText,R.id.textView7};
        scAdapter1 = new SimpleCursorAdapter(this, R.layout.custom_edittext_layout, cursor, from1, to1,2);
       // new String[] { Definition.Item.TITLE, Definition.Item.CREATE_DATE }, new int[] { R.id.title, R.id.createDate});
//здесь надо поменять дату
      /* scAdapter1.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {

                if (aColumnIndex == 1) {
                    String createDate = aCursor.getString(aColumnIndex);
                    TextView textView = (TextView) aView;
                    textView.setText("date:" + createDate);
                    return true;
                }

                return false;
            }
        });*/

        lvData2 = (ListView) findViewById(R.id.lvData2);
        lvData2.setAdapter(scAdapter1);
        // добавляем контекстное меню к списку
        registerForContextMenu(lvData2);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Option");
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);

    }
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            adapter.delRec1(acmi.id);
            cursor.requery();
            updateListView();

            ArrayList<String> names2= new ArrayList<>();;
            ArrayList<String> names3= new ArrayList<>();;
                cursor = adapter.getAllData2((int) userId);
                cursor.moveToFirst();
                //расчитываем сумму зад и период
                while (!cursor.isAfterLast()) {
                    names2.add(cursor.getString(cursor.getColumnIndex("summ2")));
                    cursor.moveToNext();
                }
                user  = adapter.getUser(userId);
                names3.add(String.valueOf(user.getSumm()));
                for (int i = 0; i < names2.size() ; i++) {
                    int s = Integer.parseInt(names3.get(i));
                    int s1 = Integer.parseInt(names2.get(i));
                    names3.add(String.valueOf(s - s1));
                }
                int summD1 = Integer.parseInt(names3.get(names3.size()-1));
            Log.d("summD1", String.valueOf(summD1) + " остаток");
                user.setSummD1(summD1);
                adapter.update(user);

            return true;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onClick(View v) {

            // TODO Auto-generated method stub

            if (v == textDate1) {

                //break;
                Calendar c = Calendar.getInstance();
                den = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                god = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date1 = year + "." + (month + 1) + "." + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                        Date datenorm;
                        //String date = null;
                        try {
                            datenorm = dateFormat.parse(date1);
                            date = dateFormat.format(datenorm.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (dateFormat.parse(date).before(dateFormat.parse("1992.01.01"))) {
                                Toast.makeText(getApplicationContext(), "Дата ачало не может быть меньше 1992.01.01", Toast.LENGTH_SHORT).show();

                            } else {

                                textDate1.setText(date);
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }
                        , god, mes, den);

                datePickerDialog.show();
        }
        if (v == textDate2) {
            Calendar c = Calendar.getInstance();
            den = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            god = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date1 = year + "." + (month + 1) + "." + dayOfMonth;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                    Date datenorm;
                    String date = null;
                    try {
                        datenorm = dateFormat.parse(date1);
                        date = dateFormat.format(datenorm.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (dateFormat.parse(date).before(dateFormat.parse(textDate1.getText().toString()))) {
                            Toast.makeText(getApplicationContext(), "Дата ачало не может быть меньше 1992.01.01", Toast.LENGTH_SHORT).show();

                        }else {

                            textDate2.setText(date);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
                    , god, mes, den);
            datePickerDialog.show();
        }
                  user = adapter.getUser(userId);
        if (v == btnCreate) {
            mContext=this;

            CustomDialogFragment dialog = new CustomDialogFragment();
            Bundle args = new Bundle();
            args.putString("phone1", String.valueOf(userId));
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");
           }





        if (v == button) {

            String name = textView13.getText().toString();
            int summ = Integer.parseInt(editText.getText().toString());
            String date_1 = textDate1.getText().toString();
            String date_2 = textDate2.getText().toString();
            int proz = Integer.parseInt(prView.getText().toString());
            int ID2 = (int) userId;
            int summ2 = user.getSumm2();
            int summD1= user.getSummD1();
            user = new User(userId, name, summ, summ2, ID2, date_1, date_2, date_3, checkBox, proz,summD1);
            adapter.open();
            adapter.update(user);
            adapter.update2(user);
            //adapter.close();
            final Intent intent = new Intent(this, MainActivity2.class);
            Bundle b = new Bundle();
            b.putInt("userId", (int) userId);
            intent.putExtras(b);
            startActivity(intent);

        }


    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity3.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void updateListView() {
        // Get an updated cursor with any changes to the database.
        Cursor updatedCursor = adapter.getAllData2((int) userId);

        // Update the ListAdapter.
        scAdapter1.changeCursor(updatedCursor);
    }
    @Override public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}




















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
    CheckBox checkBox1;
    Context mContext;
    User user;
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

//user.setCheckBox(0);
       // Log.d("POL", String.valueOf(user.getCheckBox()));
//        User user = adapter.getUser(userId);
        checkBox = user.getCheckBox();

            boolean checkBox2 = user.getCheckBox() > 0;
        Log.d("array", String.valueOf(user.getCheckBox()));
            checkBox1.setChecked(checkBox2);
            if (checkBox2){

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

                    prView.setVisibility(View.INVISIBLE);
                    textView5.setVisibility(View.INVISIBLE);
                }

                else{

                    // Your code
                    checkBox=0;
                    prView.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                }

            }
        });
        Log.d("array", String.valueOf(user.getCheckBox()));
        cursor = adapter.getAllData2((int) userId);
        startManagingCursor(cursor);


        String[] from1 = new String[] {DB1.COLUMN_DATE2, DB1.COLUMN_TXT2};
        int[] to1 = new int[] { R.id.editText,R.id.textView7};
        scAdapter1 = new SimpleCursorAdapter(this, R.layout.custom_edittext_layout, cursor, from1, to1);
       // new String[] { Definition.Item.TITLE, Definition.Item.CREATE_DATE }, new int[] { R.id.title, R.id.createDate});
//здесь надо поменять дату
       scAdapter1.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {

                if (aColumnIndex == 1) {
                    String createDate = aCursor.getString(aColumnIndex);
                    TextView textView = (TextView) aView;
                    textView.setText(/*"date:" + */createDate);
                    return true;
                }

                return false;
            }
        });

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
            // обновляем курсор
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        final Calendar c = Calendar.getInstance();
        den = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        god = c.get(Calendar.YEAR);
        if (v == textDate1) {
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
                        if (dateFormat.parse(date).before(dateFormat.parse("1992.01.01"))) {
                            Toast.makeText(getApplicationContext(), "Дата ачало не может быть меньше 1992.01.01", Toast.LENGTH_SHORT).show();

                        }else {

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
 User user = adapter.getUser(userId);
        if (v == btnCreate) {
            mContext=this;
            AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);
            //final AlertDialog.Builder alert = new AlertDialog.Builder(mContext, android.R.style.Theme_DeviceDefault_Dialog);
            final View custom_dialogalert2 = getLayoutInflater().inflate(R.layout.custom_dialogalert2,null);
            builder.setView(custom_dialogalert2);


            final TextView dateCD2 = (TextView) custom_dialogalert2.findViewById(R.id.dateCD2);
            final EditText summCD2 = (EditText) custom_dialogalert2.findViewById(R.id.summCD2);
            final AlertDialog dialog = builder.create();
            dateCD2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
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
                            dateCD2.setText(date);
                        }

                    }
                            , god, mes, den);
                    datePickerDialog.show();
                }
            });


            final Button cancel = (Button) custom_dialogalert2.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dismiss the alert dialog
                    dialog.cancel();

                }
            });

            final Button OK = (Button) custom_dialogalert2.findViewById(R.id.OK);
            //final User finalUser = user;
            final User finalUser = user;
            //final User finalUser1 = user;
            OK.setOnClickListener(new View.OnClickListener() {
               // private User user = adapter.getUser(userId);

                @Override
                public void onClick(View v) {

                    EditText summCD2 = custom_dialogalert2.findViewById(R.id.summCD2);
                    boolean retval1 = summCD2.getText().toString().isEmpty();
                    if (retval1 == true) {

                        Toast.makeText(getApplicationContext(), "Введите сумму", Toast.LENGTH_SHORT).show();
                    }
                    TextView editText1 = custom_dialogalert2.findViewById(R.id.dateCD2);
                    String date_3 = (editText1.getText().toString());


                    if (dateCD2.getText().equals("0000.00.00")) {
                        Toast.makeText(getApplicationContext(), "Введите дату", Toast.LENGTH_SHORT).show();
                    }

                        else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);


                        try {
                            if (dateFormat.parse(dateCD2.getText().toString()).before(dateFormat.parse(textDate1.getText().toString()))) {
                                Toast.makeText(getApplicationContext(), "Дата гашения не может быть меньше дата начало", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (Integer.parseInt(summCD2.getText().toString()) > finalUser.getSummD1()) {
                           Toast.makeText(getApplicationContext(), "Сумма гашения не может быть больше долга", Toast.LENGTH_SHORT).show();



                            } else {
                                        int summ2 = Integer.parseInt(summCD2.getText().toString());
                                        dialog.cancel();
                                        int ID2 = (int) userId;

                                        String name = textView13.getText().toString();
                                        int summ = Integer.parseInt(editText.getText().toString());
                                        String date_1 = textDate1.getText().toString();

                                        String date_2 = textDate2.getText().toString();
                                        int proz = Integer.parseInt(prView.getText().toString());

                                int summD1= finalUser.getSummD1();
                                User user = new User(userId, name, summ, summ2, ID2, date_1, date_2, date_3, checkBox, proz,summD1);


                                        adapter.open();
                                        if (userId > 0) {
                                            adapter.addRec2(user);

                                            adapter.update(user);
                                            // adapter.updateOst(user);
                                        } else {
                                            adapter.addRec(user);

                                            adapter.addRec2(user);
                                        }

                                        cursor.requery();
                                        // adapter.close();

                                    }

                    }
                }

            });

            dialog.show();
        }





        if (v == button) {

            String name = textView13.getText().toString();
            int summ = Integer.parseInt(editText.getText().toString());
            String date_1 = textDate1.getText().toString();
            String date_2 = textDate2.getText().toString();
            int proz = Integer.parseInt(prView.getText().toString());

            user = new User(userId, name, summ,date_1,date_2,checkBox,proz);
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

}




















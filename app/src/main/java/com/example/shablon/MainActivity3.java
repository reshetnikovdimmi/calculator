package com.example.shablon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity  {

    private static final int CM_DELETE_ID = 1;
    private static final int CM_IZM_ID = 2;
    private static final int CM_POSM_ID = 3;
    private static final int CM_HISTORY_ID = 4;
    private static final int CM_PAY_ID = 5;
    ListView lvData,lvData2;
    DB1 db;
    SimpleCursorAdapter scAdapter,scAdapter2;
    Cursor cursor;
    TextView textView10, textView11,TVproz;
    RelativeLayout linear1;
    int den, mes, god;
    Context mContext;
    Button yourButton;
    String date = null;
    Adapter ad;
    int[] to;
    String[] from;
    int checkBox;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main3);
        TVproz = (TextView) findViewById(R.id.TVproz);
       CheckBox Gk395 = (CheckBox) findViewById(R.id.Gk395);
        // открываем подключение к БД

        db = new DB1(this);
        db.open();

        // получаем курсор
        cursor = db.getAllData();
        startManagingCursor(cursor);

        // формируем столбцы сопоставления
        from = new String[] {DB1.COLUMN_TXT,DB1.COLUMN_TXT1,DB1.COLUMN_SUMMD1};
        to = new int[] { R.id.tvText,R.id.tvText1,R.id.ostDol};

        // создааем адаптер и настраиваем список
        //scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to,0);
        scAdapter = new Adapter(this,R.layout.item,cursor,from,to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setItemsCanFocus(true);
        final View item = getLayoutInflater().inflate(R.layout.item,null);
        yourButton = (Button) item.findViewById(R.id.btmMenu);
        lvData.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvData);
       // getSupportLoaderManager().initLoader(0, null, this);
        db.open();

        //lvData2 = (ListView) findViewById(R.id.lvData2);
        //lvData2.setAdapter(scAdapter2);


        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) scAdapter.getItem(position);

                if(cursor!=null) {
                    CustomDialogFragment dialog = new CustomDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("phone", String.valueOf(id));
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), "custom");
                    }
            }
        });

    }

    // обработка нажатия кнопки
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onButtonClick(View view) {
        mContext=this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);

        final View custom_dialogalert = getLayoutInflater().inflate(R.layout.custom_dialogalert,null);
        builder.setView(custom_dialogalert);


        final TextView textView10 = (TextView) custom_dialogalert.findViewById(R.id.textView10);
        final AlertDialog dialog = builder.create();
        final Calendar c = Calendar.getInstance();
        den = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        god = c.get(Calendar.YEAR);
         textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date1 = year + "." + (month + 1) + "." + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                        Date datenorm;

                        try {
                            datenorm = dateFormat.parse(date1);
                            date = dateFormat.format(datenorm.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                            textView10.setText(date);
                        date=null;
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        dateSetListener, god, mes, den);
                //Toast.makeText(getApplicationContext(), "Введите дату", Toast.LENGTH_SHORT).show();
                datePickerDialog.show();

            }
        });
        final TextView textView11 = (TextView) custom_dialogalert.findViewById(R.id.textView11);
        //на будующее установка текущей даты


      /*  String tdata = (DateUtils.formatDateTime(mContext,c.getTimeInMillis(),
                 DateUtils.FORMAT_SHOW_YEAR));
        textView11.setText(tdata);*/
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date1 = year + "." + (month + 1) + "." + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                        Date datenorm;

                        try {
                            datenorm = dateFormat.parse(date1);
                            date = dateFormat.format(datenorm.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textView11.setText(date);
                        date=null;
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        dateSetListener, god, mes, den);
                datePickerDialog.show();


            }

        });
        final CheckBox ProzP = (CheckBox) custom_dialogalert.findViewById(R.id.ProzP);
        final CheckBox Gk395 = (CheckBox) custom_dialogalert.findViewById(R.id.Gk395);
        final TextView TVproz = (TextView) custom_dialogalert.findViewById(R.id.TVproz);
        final TextView TVproz1 = (TextView) custom_dialogalert.findViewById(R.id.TVproz1);
        ProzP.setChecked(true);
        Gk395.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    // Your code
                    checkBox =1;
                    //Toast.makeText(getApplicationContext(), "Еще в разработке", Toast.LENGTH_SHORT).show();
                    ProzP.setChecked(false);
                   TVproz.setVisibility(View.INVISIBLE);
                    TVproz1.setVisibility(View.INVISIBLE);

                }

                else{
                    ProzP.setChecked(true);
                    // Your code
                    checkBox=0;
                    TVproz.setVisibility(View.VISIBLE);
                    TVproz1.setVisibility(View.VISIBLE);
                }
            }
        });

       // final TextView TVproz = (TextView) custom_dialogalert.findViewById(R.id.TVproz);
        ProzP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    // Your code
                    checkBox =0;
                    //Toast.makeText(getApplicationContext(), "Еще в разработке", Toast.LENGTH_SHORT).show();
                    Gk395.setChecked(false);
                    TVproz.setVisibility(View.VISIBLE);
                    TVproz1.setVisibility(View.VISIBLE);
                }

                else{
                    Gk395.setChecked(true);
                    // Your code
                    checkBox=1;
                    TVproz.setVisibility(View.INVISIBLE);
                    TVproz1.setVisibility(View.INVISIBLE);
                }
            }
        });

        final Button cancel = (Button) custom_dialogalert.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                dialog.cancel();

              }
        });
        final Button OK = (Button) custom_dialogalert.findViewById(R.id.OK);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText editText = custom_dialogalert.findViewById(R.id.date1);
                String name = editText.getText().toString();
                EditText editText1 = custom_dialogalert.findViewById(R.id.date2);
                String name11 = (editText1.getText().toString());
                EditText TVproz = custom_dialogalert.findViewById(R.id.TVproz);
                int proz = Integer.parseInt((TVproz.getText().toString()));
                String date_1 = textView10.getText().toString();
                String date_2 = textView11.getText().toString();
                boolean retval = name.isEmpty();
                boolean retval1 = name11.isEmpty();
                //boolean retval2 = date.isEmpty();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);

                if (retval == true) {
                    Toast.makeText(getApplicationContext(), "Введите наименование долга", Toast.LENGTH_SHORT).show();
                }
                    if (retval1 == true) {
                        Toast.makeText(getApplicationContext(), "Введите сумму", Toast.LENGTH_SHORT).show();
                    }

                if (textView10.getText().equals("0000.00.00")) {
                    Toast.makeText(getApplicationContext(), "Введите дату начала", Toast.LENGTH_SHORT).show();
                }
                    if (textView11.getText().equals("0000.00.00")) {
                        Toast.makeText(getApplicationContext(), "Введите дату конец", Toast.LENGTH_SHORT).show();
                }
                   else {

                        try {
                            if (dateFormat.parse(textView11.getText().toString()).before(dateFormat.parse(textView10.getText().toString()))) {
                                Toast.makeText(getApplicationContext(), "Дата конец меньше дата начало", Toast.LENGTH_SHORT).show();
                            }
                            if (dateFormat.parse(textView10.getText().toString()).before(dateFormat.parse("1992.01.01"))) {
                                Toast.makeText(getApplicationContext(), "Дата ачало не может быть меньше 1992.01.01", Toast.LENGTH_SHORT).show();
                            }else {
                                dialog.cancel();
                                //чек бокс по умолчанию
                                db.open();
                                int nam = Integer.parseInt(name11);
                                User user = new User(name,checkBox,nam,nam,date_1,date_2,proz);

                                db.addRec(user);
                                cursor.requery();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
            }

        });

       dialog.show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Option");
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
        menu.add(0, CM_IZM_ID, 0, R.string.to_change_record);
        menu.add(0, CM_POSM_ID, 0, R.string.view_the_calculation);
        menu.add(0, CM_HISTORY_ID, 0, R.string.history);
        menu.add(0, CM_PAY_ID, 0, R.string.Partial_payment);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);

            cursor.requery();
            return true;
        }
            // обновляем курсор
            if (item.getItemId() == CM_IZM_ID) {
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //Log.d("POL", String.valueOf(id));
                intent.putExtra("id", acmi.id);
                intent.putExtra("click", 25);
                startActivity(intent);

            cursor.requery();
            return true;
        }
        if (item.getItemId() == CM_POSM_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Intent intent = new Intent(this, MainActivity2.class);
            Bundle b = new Bundle();
            b.putInt("userId", (int) acmi.id);
            intent.putExtras(b);
            startActivity(intent);

            cursor.requery();
            return true;
        }



        if (item.getItemId() == CM_HISTORY_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
           /* final Intent intent = new Intent(this, MainActivity2.class);
            Bundle b = new Bundle();
            b.putInt("userId", (int) acmi.id);
            intent.putExtras(b);
            startActivity(intent);
            CustomDialogHistory dialog = new CustomDialogHistory();
            Bundle args = new Bundle();
            args.putString("phone", String.valueOf(acmi));
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");*/
            cursor = db.getAllData2((int) acmi.id);
            startManagingCursor(cursor);
            String[] from1 = new String[] {DB1.COLUMN_DATE2, DB1.COLUMN_TXT2};
            int[] to1 = new int[] { R.id.Dolg,R.id.OstDolg};
            scAdapter2 = new SimpleCursorAdapter(this, R.layout.history_dialog, cursor, from1, to1);
            final AlertDialog.Builder alert = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Dialog);
            LayoutInflater inflater = getLayoutInflater();



            View history = inflater.inflate(R.layout.history_dialog, null);
            TextView NameD = (TextView) history.findViewById(R.id.NameD);
            TextView title = new TextView(this);
// You Can Customise your Title here

            title.setBackgroundColor(Color.DKGRAY);
            title.setPadding(10, 10, 10, 10);
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.WHITE);
            title.setTextSize(20);
            Cursor cursor = db.getAllDataT2((int) acmi.id);
            if(cursor.moveToFirst()){
                do{

                    String name = cursor.getString(cursor.getColumnIndex(DB1.COLUMN_TXT));
                    //int year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
                    //arrayAdapter.add(name);
                    //alert.setTitle( name);
                    title.setText(name);
                    //NameD.setText(name);
                }
                while (cursor.moveToNext());
            }

            alert.setCustomTitle(title);
            cursor.close();

                   // alert.setAdapter(scAdapter2);

                    //alert.setAdapter(typAdapter, this);
            alert.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alert.setAdapter(scAdapter2,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = String.valueOf(scAdapter.getItem(which));
                            /*AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    getApplicationContext(), android.R.style.Theme_DeviceDefault_Dialog);
                            builderInner.setMessage(strName);
                            builderInner.setTitle("Your Selected Item is");


                            builderInner.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builderInner.show();*/
                        }
                    });
            alert.show();


           // break;

            return true;
        }
        if (item.getItemId() == CM_PAY_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
           /* final Intent intent = new Intent(this, MainActivity2.class);
            Bundle b = new Bundle();
            b.putInt("userId", (int) acmi.id);
            intent.putExtras(b);
            startActivity(intent);*/


            CustomDialogFragment dialog = new CustomDialogFragment();
            Bundle args = new Bundle();
            args.putString("phone", String.valueOf(acmi.id));
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                //Toast.makeText(this, R.string.text, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Dialog);

                //alert.setTitle("Title");
                alert.setMessage("Message");

                final View custom_dialogalert = getLayoutInflater().inflate(R.layout.custom_dialogalert3,null);
                alert.setView(custom_dialogalert);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });



                alert.show();


                break;

        }
        return true;
    }
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }


}

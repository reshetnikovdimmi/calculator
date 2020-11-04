package com.example.shablon;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomDialogFragment extends DialogFragment {
    int den, mes, god;
    Cursor cursor;
    DB1 adapter;
    Date_i_Time ad;
    User user;
    int summ2;
    int ID2;
    String date_3;
    ArrayList<String> names2= new ArrayList<>();
    ArrayList<String> names3 = new ArrayList<>();

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
               final Calendar c = Calendar.getInstance();
        den = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        god = c.get(Calendar.YEAR);

        final String phone = getArguments().getString("phone");


        final String phone1 = getArguments().getString("phone1");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),android.R.style.Theme_DeviceDefault_Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View custom_dialogalert2 = inflater.inflate(R.layout.custom_dialogalert2,null);
        builder.setView(custom_dialogalert2);

        final TextView dateCD2 = (TextView) custom_dialogalert2.findViewById(R.id.dateCD2);
        final EditText summCD2 = (EditText) custom_dialogalert2.findViewById(R.id.summCD2);
        final AlertDialog dialog = builder.create();
        final Button cancel = (Button) custom_dialogalert2.findViewById(R.id.cancel);
        final Button OK = (Button) custom_dialogalert2.findViewById(R.id.OK);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();

            }
        });

        dateCD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

        OK.setOnClickListener(new View.OnClickListener() {

           // DB1 adapter = new DB1(this);

            @Override
            public void onClick(View v) {
                if (phone == null) {
                    ID2 = Integer.parseInt(phone1);
                } else {
                    ID2 = Integer.parseInt(phone);
                }
                adapter = new DB1(getContext());
                //ad = new Date_i_Time();
                adapter.open();
                user = adapter.getUser(ID2);

                date_3 = dateCD2.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                if (date_3.equals("0000.00.00")) {
                    Toast.makeText(getContext(), "Введите дату ", Toast.LENGTH_SHORT).show();

                }
                try {
                    if (dateFormat.parse(date_3).before(dateFormat.parse(user.getDate_1()))) {
                        Toast.makeText(getContext(), "Долг невозможно вернуть раньше чем взяли", Toast.LENGTH_SHORT).show();
                    }

                if (dateFormat.parse(date_3).before(dateFormat.parse("1992.01.01"))) {
                    Toast.makeText(getContext(), "Дата ачало не может быть меньше 1992.01.01", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
                boolean retval = summCD2.getText().toString().isEmpty();
                if (retval == true) {
                    Toast.makeText(getContext(), "Введите сумму ", Toast.LENGTH_SHORT).show();

                }
                summ2 = Integer.parseInt(summCD2.getText().toString());
                if (summ2 > user.getSummD1()) {
                    Toast.makeText(getContext(), "Сумма больше долга ", Toast.LENGTH_SHORT).show();

                }
                else {
                    cursor = adapter.getAllData2(ID2);
                    names2.add(String.valueOf(summ2));
                    cursor.moveToFirst();
                    //расчитываем сумму зад и период
                    while (!cursor.isAfterLast()) {
                        names2.add(cursor.getString(cursor.getColumnIndex("summ2")));
                        cursor.moveToNext();
                    }

                    user = adapter.getUser(ID2);
                    names3.add(String.valueOf(user.getSumm()));

                    for (int i = 0; i < names2.size(); i++) {
                        int s = Integer.parseInt(names3.get(i));
                        int s1 = Integer.parseInt(names2.get(i));
                        names3.add(String.valueOf(s - s1));
                    }


                    //Toast.makeText(getContext(), String.valueOf(summ2), Toast.LENGTH_SHORT).show();
                    int summD1 = Integer.parseInt(names3.get(names3.size() - 1));

                        user = new User(summ2, ID2, date_3, summD1);

                        adapter.addRec2(user);
                        adapter.update2(user);
                        long id = ID2;
                        user = adapter.getUser(ID2);
                        user.setSummD1(summD1);
                        String name = user.getName();
                        int summ = user.getSumm();
                        String date = user.getDate_1();
                        String date1 = user.getDate_2();
                        int checkBox = user.getCheckBox();
                        int proz = user.getProz();
                        user = new User(ID2, name, checkBox, summ, summD1, date, date1, proz);
                        adapter.update(user);

                        dialog.dismiss();

                        if (phone == null) {
                            ((MainActivity) getActivity()).updateListView();

                        } else {
                            ((MainActivity3) getActivity()).updateListView();
                        }

                    }

                }

        });


        return dialog;
}

}

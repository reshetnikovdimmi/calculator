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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomDialogFragment extends DialogFragment {
   Context mContext;
    int den, mes, god, checkBox;
    Cursor cursor;
    DB1 adapter;
    User user;
    //adapter = new DB1(this);
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        den = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        god = c.get(Calendar.YEAR);

        final String phone = getArguments().getString("phone");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),android.R.style.Theme_DeviceDefault_Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View custom_dialogalert2 = inflater.inflate(R.layout.custom_dialogalert2,null);
        builder.setView(custom_dialogalert2);

        final TextView dateCD2 = (TextView) custom_dialogalert2.findViewById(R.id.dateCD2);
        final EditText summCD2 = (EditText) custom_dialogalert2.findViewById(R.id.summCD2);
        final AlertDialog dialog = builder.create();
        final Button cancel = (Button) custom_dialogalert2.findViewById(R.id.cancel);
        final Button OK = (Button) custom_dialogalert2.findViewById(R.id.OK);
       // final EditText editText1 = (EditText) custom_dialogalert2.findViewById(R.id.dateCD2);
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
        //adapter = new DB1(this);
        OK.setOnClickListener(new View.OnClickListener() {

           // DB1 adapter = new DB1(this);

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), dateCD2.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), summCD2.getText().toString(), Toast.LENGTH_SHORT).show();

               String date_3 = dateCD2.getText().toString();
               int summ2 = Integer.parseInt(summCD2.getText().toString());
               int ID2 = Integer.parseInt(phone);
                Log.d("ID2", String.valueOf(ID2));
               Toast.makeText(getContext(), String.valueOf(ID2), Toast.LENGTH_SHORT).show();
                adapter = new DB1(getContext());
                        User user = new User(summ2, ID2,date_3);
                adapter.open();
               adapter.addRec2(user);
                adapter.update2(user);

                dialog.dismiss();

                    }




        });


        return dialog;
}

}

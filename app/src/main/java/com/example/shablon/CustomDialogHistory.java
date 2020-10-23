package com.example.shablon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogHistory extends DialogFragment  {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String phone = getArguments().getString("phone");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View history = inflater.inflate(R.layout.history_dialog, null);
       // ListView lvData2 = (ListView) findViewById(R.id.LVProz);
        final String[] mChooseCats = { "Тимка", "Пушок", "Кузя" };
        final AlertDialog.Builder builder = builder1.setSingleChoiceItems(mChooseCats, -1, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog,
                                int item) {
                Toast.makeText(
                        getContext(),
                        "Любимое имя кота: "
                                + mChooseCats[item],
                        Toast.LENGTH_SHORT).show();
            }

        });
        builder.setView(history);
       // final TextView dateCD2 = (TextView) history.findViewById(R.id.TVprozt);
       // final EditText summCD2 = (EditText) history.findViewById(R.id.summCD2);
        final AlertDialog dialog = builder1.create();
       // final Button cancel = (Button) history.findViewById(R.id.cancel);
        //final Button OK = (Button) history.findViewById(R.id.OK);
        // final EditText editText1 = (EditText) custom_dialogalert2.findViewById(R.id.dateCD2);

        return dialog;
    }
}

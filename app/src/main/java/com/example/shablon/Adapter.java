package com.example.shablon;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Adapter extends SimpleCursorAdapter {
    Context ctx;
    public Adapter(Context context, int layout, Cursor cursor, String[] from, int[] to) {
        super(context, layout, cursor, from, to);
        ctx = context;
           }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        long id=getItemId(position);
        view.setTag(id);
        Button btmMenu=(Button) view.findViewById(R.id.btmMenu);
        btmMenu.setOnClickListener(ClickBtmMenu);
        btmMenu.setTag(id);
        return view;
    }

    // обработчик для кнопок
    View.OnClickListener ClickBtmMenu = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            v.showContextMenu();
           // Toast.makeText(ctx, "ID - получилось - "+v.getTag(), Toast.LENGTH_LONG).show();

        }

    };
}

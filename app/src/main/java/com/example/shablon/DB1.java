package com.example.shablon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB1 {
    private static final String DB_NAME = "mydb3";
    private static final int DB_VERSION = 1;
    public static final String DB_TABLE = "mytab1";
    public static final String DB_TABLE2 = "mytab2";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_2 = "_id";
    public static final String COLUMN_TXT = "name";
    public static final String COLUMN_TXT1 = "summ";
    public static final String COLUMN_DATE = "date_1";
    public static final String COLUMN_DATE1 = "date_2";
    public static final String COLUMN_CB = "checkBox";
    public static final String COLUMN_DATE2 = "date_3";
    public static final String COLUMN_TXT2 = "summ2";
    public static final String COLUMN_ID2 = "ID2";
    public static final String COLUMN_PROZ = "PROZ";
    public static final String COLUMN_SUMMD1 = "SUMMD1";


    private static final String DB_CREATE = "create table " + DB_TABLE + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TXT + " text," +
            COLUMN_TXT1 + " integer," +
            COLUMN_CB + " intrger," +
            COLUMN_DATE + " intrger," +
            COLUMN_DATE1 + " integer," +
            COLUMN_SUMMD1 + " integer," +
            COLUMN_PROZ + " integer);";

    private static final String DB_CREATE2 = "create table " + DB_TABLE2 + "(" +
            COLUMN_ID_2 + " integer primary key autoincrement, " +

            COLUMN_DATE2 + " integer," +
            COLUMN_TXT2 + " txt," +
            COLUMN_ID2 + " integer);";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB1(Context ctx) {
        mCtx = ctx;
    }




    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {

        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }
    public Cursor getAllDataT2(int ID2) {
        String selection = "_id = ?";
        String[] selectionArgs = {String.valueOf(ID2)};
        return mDB.query(DB_TABLE, null, selection, selectionArgs, null, null, null);
    }

    // получить все данные из таблицы DB_TABLE2
    public Cursor getAllData2date_3(long id) {
        String columns[] = {"checkBox"};
        String selection = "_id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return mDB.query(DB_TABLE, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor getAllData2(int ID2) {

        String selection = "ID2 = ?";
        String[] selectionArgs = {String.valueOf(ID2)};
        return mDB.query(DB_TABLE2, null, selection, selectionArgs, null, null, "date_3");
    }

    // добавить запись в DB_TABLE

    public void addRec(User user) {
        ContentValues cv = new ContentValues();

        cv.put(DB1.COLUMN_TXT, user.getName());
        cv.put(DB1.COLUMN_TXT1, user.getSumm());
        cv.put(DB1.COLUMN_DATE, user.getDate_1());
        cv.put(DB1.COLUMN_DATE1, user.getDate_2());
        cv.put(DB1.COLUMN_CB, user.getCheckBox());
        cv.put(DB1.COLUMN_PROZ, user.getProz());
        cv.put(DB1.COLUMN_SUMMD1, user.getSummD1());
        mDB.insert(DB_TABLE, null, cv);
    }

    // добавить запись в DB_TABLE2
    public void addRec2(User user) {

        ContentValues cv = new ContentValues();
        cv.put(DB1.COLUMN_TXT2, user.getSumm2());
        cv.put(DB1.COLUMN_DATE2, user.getDate_3());
        cv.put(DB1.COLUMN_ID2, user.getID2());
        mDB.insert(DB_TABLE2, null, cv);
    }


    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
        mDB.delete(DB_TABLE2, COLUMN_ID2 + " = " + id, null);
    }

    public void delRec1(long id) {
        mDB.delete(DB_TABLE2, COLUMN_ID + " = " + id, null);
    }

    public User getUser(long id) {
        User user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DB1.DB_TABLE, DB1.COLUMN_ID);
        Cursor cursor = mDB.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DB1.COLUMN_TXT));
            int summ = cursor.getInt(cursor.getColumnIndex(DB1.COLUMN_TXT1));
            String date = cursor.getString(cursor.getColumnIndex(DB1.COLUMN_DATE));
            String date1 = cursor.getString(cursor.getColumnIndex(DB1.COLUMN_DATE1));
            int checkBox = cursor.getInt(cursor.getColumnIndex(DB1.COLUMN_CB));
            int proz = cursor.getInt(cursor.getColumnIndex(DB1.COLUMN_PROZ));
            int SummD1 = cursor.getInt(cursor.getColumnIndex(DB1.COLUMN_SUMMD1));
            user = new User(id, name, summ, date, date1,checkBox,proz,SummD1);
        }
        cursor.close();
        return user;
    }

    public List<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = getAllData();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DB1.COLUMN_ID));
               String name = cursor.getString(cursor.getColumnIndex(DB1.COLUMN_TXT));
                //int year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
                //users.add(new User(id,name);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  users;
    }

    public ArrayList<String> getContacts(){
        Cursor cursor = getAllData();
        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<>();
        while(!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex("date_1")));
            cursor.moveToNext();
        }
        cursor.close();
        String[] output = new String[names.size()];
        for (int i = 0; i != names.size(); i++) {
            output[i] = names.get(i);
        }
        return names;
    }


    public long update(User user) {

        String whereClause = DB1.COLUMN_ID + "=" + user.getId();
        ContentValues cv = new ContentValues();
        cv.put(DB1.COLUMN_TXT, user.getName());
        cv.put(DB1.COLUMN_TXT1, user.getSumm());
        cv.put(DB1.COLUMN_DATE, user.getDate_1());
        cv.put(DB1.COLUMN_DATE1, user.getDate_2());
        cv.put(DB1.COLUMN_CB, user.getCheckBox());
        cv.put(DB1.COLUMN_PROZ, user.getProz());
        cv.put(DB1.COLUMN_SUMMD1, user.getSummD1());
        return mDB.update(DB_TABLE, cv, whereClause, null);
    }


    public long update2(User user) {

        String whereClause = DB1.COLUMN_ID + "=" + user.getId();
        ContentValues cv = new ContentValues();
        cv.put(DB1.COLUMN_TXT2, user.getSumm2());
        cv.put(DB1.COLUMN_DATE2, user.getDate_3());
        cv.put(DB1.COLUMN_ID2, user.getID2());
        return mDB.update(DB_TABLE2, cv, whereClause, null);
    }
    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }
        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            db.execSQL(DB_CREATE2);
            }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }


}

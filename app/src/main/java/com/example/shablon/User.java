package com.example.shablon;

import android.widget.CheckBox;
import android.widget.TextView;

public class User {
    private long id;
    private String name;
    private int summ;
    private int proz;
    private int summ2;
    private int SummD1;
    private int ID2;
    private int checkBox;

    public User(long id, String name, int summ, int summ2, int ID2, String date_1, String date_2, String date_3, int checkBox, int proz,int summD1) {
        this.id = id;
        this.name = name;
        this.summ = summ;
        this.summ2 = summ2;
        this.ID2 = ID2;
        this.date_1 = date_1;
        this.date_2 = date_2;
        this.date_3 = date_3;
        this.checkBox = checkBox;
        this.proz = proz;
        this.SummD1 = summD1;
    }

    public User( int summ2, int ID2, String date_3) {

        this.summ2 = summ2;
        this.ID2 = ID2;
        this.date_3 = date_3;

    }

    public void setCheckBox(int checkBox) {
        this.checkBox = checkBox;
    }

    public void setSummD1(int summD1) {
        SummD1 = summD1;
    }

    private String date_1;
    private String date_2, date_3;

    public User(long id, String name, int summ, int summ2, int ID2, String date_1, String date_2, String date_3, int checkBox, int proz) {
        this.id = id;
        this.name = name;
        this.summ = summ;
        this.summ2 = summ2;
        this.ID2 = ID2;
        this.date_1 = date_1;
        this.date_2 = date_2;
        this.date_3 = date_3;
        this.checkBox = checkBox;
        this.proz = proz;
    }

    public User(long id, int summD1) {
        this.id = id;
        this.SummD1 = summD1;
    }

    public User(String name, int checkBox, int nam,int summD1, String date_1, String date_2, int proz) {
        this.name = name;
        this.checkBox = checkBox;
        this.summ = nam;
        this.SummD1=summD1;
        this.date_1 = date_1;
        this.date_2 = date_2;
        this.proz = proz;

    }
    public User(String name, int checkBox, int nam,int summD1, String date_1, String date_2) {
        this.name = name;
        this.checkBox = checkBox;
        this.summ = nam;
        this.SummD1=summD1;
        this.date_1 = date_1;
        this.date_2 = date_2;


    }

    public int getSummD1() {
        return SummD1;
    }
    public int getProz() {
        return proz;
    }
    public User(long id, String name, int summ, String date, String date1, int checkBox,int proz) {
        this.id = id;
        this.name = name;
        this.summ = summ;
        this.date_1 = date;
        this.date_2 = date1;
       this.checkBox = checkBox;
        this.proz = proz;
    }
    public User(long id, String name, int summ, String date, String date1, int checkBox,int proz,int summD1) {
        this.id = id;
        this.name = name;
        this.summ = summ;
        this.date_1 = date;
        this.date_2 = date1;
        this.checkBox = checkBox;
        this.proz = proz;
this.SummD1 = summD1;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getCheckBox() {
        return checkBox;
    }
    public int getSumm2() {
        return summ2;
    }
    public int getID2() {
        return ID2;
    }
    public String getDate_3() {
        return date_3;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSumm() {
        return summ;
    }
    public String getDate_1() {
        return date_1;
    }
    public String getDate_2() {
        return date_2;
    }
    @Override
    public String toString() {
        return this.name + " : " + this.summ;
    }


}

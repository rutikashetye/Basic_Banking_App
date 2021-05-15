package com.rutika.bankapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RBI.db";
    public static final String TABLE1_NAME = "customertable";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "ACCNO";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "PHOTO";

    public static final String TABLE2_NAME = "transfertable";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY NOT NULL  , NAME TEXT, ACCNO TEXT, AMOUNT INTEGER, PHOTO BLOB)");
        db.execSQL("create table " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY NOT NULL , SENDER TEXT, RECIPIENT TEXT, AMOUNT INTEGER)");
    }
//column 0 - id , column 1 - name , column 2- acc no , column3- balance ,column 4 - photo
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE1_NAME);
        db.execSQL("drop table if exists "+TABLE2_NAME);
        onCreate(db);
    }
    public void insertCustomer(Integer id, String acc, String name, Integer amount, byte[] photo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_0,id);
        contentValues.put(COL_1,acc);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,amount);
        contentValues.put(COL_4,photo);
        db.insert(TABLE1_NAME,null,contentValues);
    }

    public List<String> getAllLabels(){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding Name column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    public void insertTransfer(String sender, String recipient, int amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SENDER",sender);
        contentValues.put("RECIPIENT",recipient);
        contentValues.put("AMOUNT",amt);
        db.insert(TABLE2_NAME,null,contentValues);
    }

    public Cursor getSpecificData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME+" where ID="+id,null);
        return res;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME,null);
        return res;
    }
    public Cursor getAllDataTable2(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME,null);
        return res;
    }
    public void updateBalance(int amt,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String updatequery="UPDATE "+TABLE1_NAME+" SET "+COL_3+"="+amt+" WHERE "+COL_0+"="+id;
        db.execSQL(updatequery);
    }

    public Cursor balance(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME+" where "+COL_0+"="+id,null);
        return res;
    }
}

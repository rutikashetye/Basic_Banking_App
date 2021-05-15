package com.rutika.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME=1000;
    DatabaseHelper myDB;
    private byte[] Myphoto1,Myphoto2,Myphoto3,Myphoto4,Myphoto5,Myphoto6,Myphoto7,Myphoto8,Myphoto9,Myphoto10;
    String prevStarted = "yes";
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            boolean firststart = sharedpreferences.getBoolean("firststart", true);
            if (firststart) {
                this.myDB = new DatabaseHelper(this);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(prevStarted, Boolean.TRUE);
                editor.apply();
                addData(myDB);
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(i);
                    finish();
                }
            },SPLASH_TIME);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
    public void addData(DatabaseHelper myDB){
            Bitmap icon1 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_1);
            Bitmap icon2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_2);
            Bitmap icon3 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_3);
            Bitmap icon4 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_4);
            Bitmap icon5 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_5);
            Bitmap icon6 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_6);
            Bitmap icon7 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_7);
            Bitmap icon8 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_8);
            Bitmap icon9 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_9);
            Bitmap icon10 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_10);
            Myphoto1 = profileImage(icon1);
            Myphoto2 = profileImage(icon2);
            Myphoto3 = profileImage(icon3);
            Myphoto4 = profileImage(icon4);
            Myphoto5 = profileImage(icon5);
            Myphoto6 = profileImage(icon6);
            Myphoto7 = profileImage(icon7);
            Myphoto8 = profileImage(icon8);
            Myphoto9 = profileImage(icon9);
            Myphoto10 = profileImage(icon10);

            myDB.insertCustomer(1, "RBI001", "Rutika Shetye", 1000, Myphoto1);
            myDB.insertCustomer(2, "RBI002", "Abhishek Polekar", 1000, Myphoto2);
            myDB.insertCustomer(3, "RBI003", "Trupti Ingavale", 1000, Myphoto3);
            myDB.insertCustomer(4, "RBI004", "Harshala Ardekar", 1000, Myphoto4);
            myDB.insertCustomer(5, "RBI005", "Shivani Shirke", 1000, Myphoto5);
            myDB.insertCustomer(6, "RBI006", "Supriya Thombare", 1000, Myphoto6);
            myDB.insertCustomer(7, "RBI007", "Supriya Shelar", 1000, Myphoto7);
            myDB.insertCustomer(8, "RBI008", "Sanika Polekar", 1000, Myphoto8);
            myDB.insertCustomer(9, "RBI009", "Vrushali Shetye", 1000, Myphoto9);
            myDB.insertCustomer(10, "RBI010", "Swamiprasad Shetye", 1000, Myphoto10);
    }
    //Convert bitmap to bytes
    private byte[] profileImage(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();

    }
}
package com.rutika.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class SuccessActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView t1;
    GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        getSupportActionBar().hide();

        t1=(TextView)findViewById(R.id.Success_Fail);
        gif=(GifImageView)findViewById(R.id.gif);

        Intent intent = getIntent();
        String sender_name = intent.getExtras().getString("Sender Name");
        int sender_balance = intent.getExtras().getInt("Current Balance");
        int sender_position = intent.getExtras().getInt("Sender Position");
        String RecName = intent.getExtras().getString("Reciever Name");
        int rec_position = intent.getExtras().getInt("Reciever Position");
        String amt= intent.getExtras().getString("Amt_to_transfer");

        myDb = new DatabaseHelper(this);

        Cursor res2 = myDb.balance(rec_position);//current balance of Reciever
        int Sender_balance_transfer = 0;
        int Tran_Amt = Integer.parseInt(amt);
        res2.moveToFirst();

        int RecBalance_transfer = Integer.parseInt(res2.getString(3));
        if (sender_balance < Tran_Amt) {
            gif.setImageResource(R.drawable.error_gif);
            t1.setText("Transaction Failed Due To Insufficient Balance");
            Toast.makeText(SuccessActivity.this, "Insufficient Balance", Toast.LENGTH_LONG).show();
        } else {
            Sender_balance_transfer = sender_balance - Tran_Amt;
            RecBalance_transfer = RecBalance_transfer + Tran_Amt;
            myDb.updateBalance(Sender_balance_transfer, sender_position);
            myDb.updateBalance(RecBalance_transfer, rec_position);
            myDb.insertTransfer(sender_name, RecName, Tran_Amt);
            gif.setImageResource(R.drawable.success);
            t1.setText("Transaction SuccessFul");
        }
    }

    public void backpress(View view) {
        Intent i = new Intent(SuccessActivity.this, CustomerActivity.class);
        startActivity(i);
        finish();
    }
}
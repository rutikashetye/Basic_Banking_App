package com.rutika.bankapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerDetails extends AppCompatActivity {
    ImageView Image;
    DatabaseHelper myDb;
    TextView name,acc,balance;
    ImageButton Transfer, hist;
    int value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("Position");
        value = position+1;

        Image = (ImageView)findViewById(R.id.CustomerImage);
        name =(TextView)findViewById(R.id.CustomerName);
        hist=(ImageButton)findViewById(R.id.history);
        Transfer=(ImageButton)findViewById(R.id.transfer);
        acc =(TextView)findViewById(R.id.CustomerAccNo);
        balance =(TextView)findViewById(R.id.CustomerBalance);
        myDb = new DatabaseHelper(this);

        Cursor res = myDb.getSpecificData(value);
        res.moveToFirst();
        acc.setText(res.getString(2));//acc
        name.setText(res.getString(1));//name
        int amt = Integer.valueOf(res.getString(3));
        balance.setText("Rs: "+amt);//balance

        byte[] imgbytes =res.getBlob(4);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,imgbytes.length);
        Image.setImageBitmap(bitmap);

        Transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetails.this,TransferDetails.class);
                i.putExtra("Sender Name",name.getText());
                i.putExtra("Current Balance",amt);
                i.putExtra("Sender Position",value);
                startActivity(i);
            }
        });
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetails.this,HistoryActivity.class);
                startActivity(i);
            }
        });

    }


    public void onBackPressed() {
        Intent i = new Intent(CustomerDetails.this, CustomerActivity.class);
        startActivity(i);
        finish();
    }
}
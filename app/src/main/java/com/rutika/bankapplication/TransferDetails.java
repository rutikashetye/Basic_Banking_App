package com.rutika.bankapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TransferDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView sendname,currbal;
    EditText amt;
    Spinner spinnerrecname;
    DatabaseHelper myDb;
    String RecName;
    int rec_position;
    Button cancel,send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);
        getSupportActionBar().hide();
        myDb = new DatabaseHelper(this);
        sendname = (TextView) findViewById(R.id.idTVSenderName);
        currbal = (TextView) findViewById(R.id.idTVSenderCurrBalance);
        amt = (EditText) findViewById(R.id.idTVsendAmt);
        cancel = (Button) findViewById(R.id.idTVCancel);
        send = (Button) findViewById(R.id.idTVSend);
        spinnerrecname = findViewById(R.id.spinnerRec);
        spinnerrecname.setOnItemSelectedListener(this);
        loadSpinnerData();

        Intent intent = getIntent();
        String sender_name = intent.getExtras().getString("Sender Name");
        int sender_balance = intent.getExtras().getInt("Current Balance");
        int sender_position = intent.getExtras().getInt("Sender Position");

        sendname.setText(sender_name);
        currbal.setText(String.valueOf(sender_balance));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransferDetails.this, CustomerActivity.class);
                startActivity(i);
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TransferDetails.this);
                builder.setTitle("Enter 4 Digit Pin to Continue");

                final EditText input = new EditText(TransferDetails.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pin = input.getText().toString();
                        if (pin.equals("1234")) {
                            Intent intent1 = new Intent(TransferDetails.this, SuccessActivity.class);
                            intent1.putExtra("Sender Name", sender_name);
                            intent1.putExtra("Current Balance", sender_balance);
                            intent1.putExtra("Sender Position", sender_position);
                            intent1.putExtra("Reciever Name", RecName);
                            intent1.putExtra("Reciever Position", rec_position);
                            intent1.putExtra("Amt_to_transfer",amt.getText().toString());
                            startActivity(intent1);
                            finish();
                        }
                        else{
                            Toast.makeText(TransferDetails.this, "Please Enter Correct Pin", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();
            }
        });
    }
    private void loadSpinnerData() {
        myDb = new DatabaseHelper(this);
        List<String> labels = myDb.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerrecname.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
        RecName = spinnerrecname.getSelectedItem().toString();
        rec_position = spinnerrecname.getSelectedItemPosition() + 1;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
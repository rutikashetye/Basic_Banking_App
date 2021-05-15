package com.rutika.bankapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity implements CourseAdapter.mOnClickListener{
    // creating variables for
    // our ui components.
    private RecyclerView courseRV;
    ImageButton hist;
    DatabaseHelper myDb;
    // variable for our adapter
    // class and array list
    private CourseAdapter adapter;
    private ArrayList<CourseModal> courseModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        courseRV = findViewById(R.id.idRVCourses);
        myDb = new DatabaseHelper(this);
        hist=(ImageButton)findViewById(R.id.history);

        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerActivity.this,HistoryActivity.class);
                startActivity(i);
                finish();
            }
        });

        buildRecyclerView();
    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<CourseModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (CourseModal item : courseModalArrayList) {
            if (item.getCourseName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {
        courseModalArrayList = new ArrayList<>();
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        while (res.moveToNext()) {

            courseModalArrayList.add(new CourseModal(res.getString(1), "Account No: "+res.getString(2), "Balance: "+res.getString(3)));
        }

        adapter = new CourseAdapter(courseModalArrayList, CustomerActivity.this,this::mOnclick);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(adapter);
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void onBackPressed() {
            Intent i = new Intent(CustomerActivity.this,MenuActivity.class);
            startActivity(i);
            finish();
    }

    //Recycler view touch
    @Override
    public void mOnclick(int position) {
        int pos = position;
        Intent i = new Intent(CustomerActivity.this,CustomerDetails.class);
        i.putExtra("Position",pos);
        startActivity(i);
        finish();
    }
}
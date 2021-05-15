package com.rutika.bankapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView HistoryRV;
    private HistoryAdapter adapter;
    DatabaseHelper myDb;
    private ArrayList<HistoryModal> ArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        myDb = new DatabaseHelper(this);
        HistoryRV = findViewById(R.id.HistoryRV);
        buildRecyclerView();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
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
        ArrayList<HistoryModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (HistoryModal item : ArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getSender().toLowerCase().contains(text.toLowerCase()) || item.getReceiver().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
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

        // below line we are creating a new array list
       ArrayList = new ArrayList<>();
        Cursor res = myDb.getAllDataTable2();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        while (res.moveToNext()) {

            ArrayList.add(new HistoryModal(res.getString(1), res.getString(2), res.getInt(3)));
        }
        // below line is to add data to our array list.

        adapter = new HistoryAdapter(ArrayList, HistoryActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        HistoryRV.setHasFixedSize(true);
        HistoryRV.setLayoutManager(manager);
        HistoryRV.setAdapter(adapter);
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void onBackPressed() {
        Intent i = new Intent(HistoryActivity.this,MenuActivity.class);
        startActivity(i);
        finish();
    }
}
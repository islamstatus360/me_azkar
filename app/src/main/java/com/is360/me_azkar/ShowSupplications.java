package com.is360.me_azkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;

public class ShowSupplications extends AppCompatActivity {

    ArrayList<Model> arrayList;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    String data = MainActivity.getSupplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        initToolbar();

        recyclerView = findViewById(R.id.recycler_view);
        databaseHelper = new DatabaseHelper(this);
        displayRow();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(myIntent);
        finish();
        return true;
    }*/

    private void initToolbar() {
        try
        {
            //this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if(data == "supplication_morning") {
                this.getSupportActionBar().setTitle("Morning Supplications");
            }
            else {
                this.getSupportActionBar().setTitle("Evening Supplications");
            }

            // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        catch (NullPointerException e){}
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void displayRow() {
        arrayList = new ArrayList<>(databaseHelper.getRowData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Adapter adapter = new Adapter(getApplicationContext(), this, arrayList);
        recyclerView.setAdapter(adapter);
    }
}
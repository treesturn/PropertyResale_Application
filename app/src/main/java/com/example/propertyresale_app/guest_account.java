package com.example.propertyresale_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class guest_account extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView Resale_Houses_Table;
    ArrayList <String> SN, town, price;
    DatabaseHelper databaseHelper;
    Property_RecyclerViewAdapater adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_account);

        databaseHelper = new DatabaseHelper(this, "alpha_SQLite.db", 1);
        SN = new ArrayList<>();
        town = new ArrayList<>();
        price = new ArrayList<>();
        Resale_Houses_Table = findViewById(R.id.Resale_Houses_Table);
        adapater = new Property_RecyclerViewAdapater(this, SN, town, price,this);
        Resale_Houses_Table.setAdapter(adapater);
        Resale_Houses_Table.setLayoutManager(new LinearLayoutManager(this));
        displaydata();


    }

    private void displaydata()
    {
        Cursor cursor = databaseHelper.getHousing();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                SN.add(cursor.getString(0));
                town.add(cursor.getString(1));
                price.add(cursor.getString(2));
            }
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}
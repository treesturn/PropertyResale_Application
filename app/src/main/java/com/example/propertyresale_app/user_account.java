package com.example.propertyresale_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class user_account extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView Resale_Houses_Table;
    ArrayList<String> SN, town, price;
    DatabaseHelper databaseHelper;
    Property_RecyclerViewAdapater adapater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user);

        databaseHelper = new DatabaseHelper(this, "alpha_SQLite.db", 1);
        SN = new ArrayList<>();
        town = new ArrayList<>();
        price = new ArrayList<>();
        Resale_Houses_Table = findViewById(R.id.Resale_Houses_Table);
        adapater = new Property_RecyclerViewAdapater(this, SN, town, price,this);
        Resale_Houses_Table.setAdapter(adapater);
        Resale_Houses_Table.setLayoutManager(new LinearLayoutManager(this));
        displaydata();

        TextView username_text = findViewById(R.id.username_text);
        String username = getIntent().getStringExtra("username");
        username_text.setText(username);

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
        Intent c = new Intent(user_account.this, Item_page.class);

        c.putExtra("sn", SN.get(position));
        c.putExtra("town", town.get(position));
        c.putExtra("price", price.get(position));

        startActivity(c);
    }
}
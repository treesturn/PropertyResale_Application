package com.example.propertyresale_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class user_account extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView Resale_Houses_Table;
    ArrayList<String> SN, town, price;
    DatabaseHelper databaseHelper;
    Property_RecyclerViewAdapater adapater;

    String sql_statement = "";


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

        Button filter_button = findViewById(R.id.FilterButton);

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();
            }
        });

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

    private void showDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);


        TextView max_price = (TextView) dialog.findViewById(R.id.max_price);
        Button Savebutton = dialog.findViewById(R.id.SaveButton);


        Savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sql_statement = "select housing_sn, town, resale_price from housing_list where resale_price <= " + max_price.getText().toString() + " limit 20";
                dialog.dismiss();
                Toast.makeText(user_account.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
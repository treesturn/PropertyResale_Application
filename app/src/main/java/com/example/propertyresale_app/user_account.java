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
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class user_account extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView Resale_Houses_Table;
    ArrayList<String> SN, town, price;
    DatabaseHelper databaseHelper;
    Property_RecyclerViewAdapater adapater;

    String[] towns = {"ANG MO KIO", "BEDOK", "BISHAN"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapteritems;

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

        Button services_button = findViewById(R.id.ServicesButton);


        services_button.setOnClickListener(new View.OnClickListener() {
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

        TextView SN2 = dialog.findViewById(R.id.SN2);
//        TextView town2 = dialog.findViewById(R.id.town2);
//        TextView interestedbuyers2 = dialog.findViewById(R.id.interestedbuyers2);
        Button Exitbutton = dialog.findViewById(R.id.ExitButton);

        SN2.setText(databaseHelper.gettransactinfo());

        Exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Toast.makeText(user_account.this, "Exit", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        autoCompleteTxt = dialog.findViewById(R.id.town_dropdown);
        adapteritems = new ArrayAdapter<String>(this, R.layout.town_list, towns);
        autoCompleteTxt.setAdapter(adapteritems);

        TextView avgprice = dialog.findViewById(R.id.avgprice);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                String price = databaseHelper.getavgprice(item);
                avgprice.setText("  $"+price);
            }
        });

        Button search = dialog.findViewById(R.id.SearchButton);
        TextView data = dialog.findViewById(R.id.data);
        TextView similarprice = dialog.findViewById(R.id.similarprice);
        String x = similarprice.getText().toString();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.setText(databaseHelper.getpairofsimilarprice(x));
            }
        });
    }

}
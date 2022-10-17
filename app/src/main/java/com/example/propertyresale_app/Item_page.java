package com.example.propertyresale_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Item_page extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);

        databaseHelper = new DatabaseHelper(this, "alpha_SQLite.db", 1);

//        TextView SN_item = findViewById(R.id.SN_item);
        TextView town_item = findViewById(R.id.town_item);
        TextView price_item = findViewById(R.id.price_item);
        TextView flattype_item = findViewById(R.id.flattype_item);
        TextView storey_item = findViewById(R.id.storey_item);
        TextView area_item = findViewById(R.id.area_item);
        TextView model_item = findViewById(R.id.model_item);
        TextView agent_info_item = findViewById(R.id.agent_info_item);
        TextView interestedbuyers_item = findViewById(R.id.interestedbuyers_item);

        String sn = getIntent().getStringExtra("sn");
        String town = getIntent().getStringExtra("town");
        String price = "$"+(getIntent().getStringExtra("price")) ;


//        SN_item.setText(sn);
        town_item.setText(town);
        price_item.setText(price);
        flattype_item.setText(databaseHelper.gethousingtype(sn));
        storey_item.setText(databaseHelper.getstoreytype(sn));
        area_item.setText(databaseHelper.getareatype(sn));
        model_item.setText(databaseHelper.getmodeltype(sn));
        agent_info_item.setText(databaseHelper.getagentinfo(sn));
        interestedbuyers_item.setText(databaseHelper.getnumberofinterestedbuyers(sn));


    }
}
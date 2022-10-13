package com.example.propertyresale_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** variables declared - start */

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.loginButton);

//        String queried_password = "1234";
        /** end */

        databaseHelper = new DatabaseHelper(this, "alpha_SQLite.db", 1);
        try{
            databaseHelper.CheckDb();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        try{
            databaseHelper.OpenDatabase();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        try{
            Toast.makeText(MainActivity.this, databaseHelper.getpassword(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }




        /** Login Button pressed - start */
//        String finalQueried_password = queried_password;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals(databaseHelper.getusername()) && password.getText().toString().equals(databaseHelper.getpassword()))
                {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Login unsuccessful, credentials incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
        /** end */
    }
}
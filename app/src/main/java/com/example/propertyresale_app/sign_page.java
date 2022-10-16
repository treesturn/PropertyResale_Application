package com.example.propertyresale_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class sign_page extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);

        /** variables declared - start */

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button guestSignIn = (Button) findViewById(R.id.guest_signin);

//        String queried_password = "1234";
        /** end */

        databaseHelper = new DatabaseHelper(this, "alpha_SQLite.db", 1);
        try{
            databaseHelper.CheckDb();
        }catch (Exception e){
            Toast.makeText(sign_page.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        try{
            databaseHelper.OpenDatabase();
        }catch (Exception e){
            Toast.makeText(sign_page.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        try{
//            Toast.makeText(sign_page.this, databaseHelper.getpassword(username.toString()), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(sign_page.this, e.toString(), Toast.LENGTH_LONG).show();
        }




        /** Login Button pressed - start */
//        String finalQueried_password = queried_password;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHelper.getusername_and_password(username.getText().toString(), password.getText().toString()))
                {
                    Toast.makeText(sign_page.this, "Login successful", Toast.LENGTH_LONG).show();
                    Intent a = new Intent(getApplicationContext(), account_user.class);
                    a.putExtra("username", username.getText().toString());
                    startActivity(a);


                }
                else
                {
                    Toast.makeText(sign_page.this, "Login unsuccessful, credentials incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
        /** end */

        /** Guest Login Button pressed - start */
//        String finalQueried_password = queried_password;
        guestSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent b = new Intent(getApplicationContext(), guest_account.class);
                startActivity(b);
            }
        });
        /** end */
    }
}
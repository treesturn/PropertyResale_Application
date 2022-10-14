package com.example.propertyresale_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context mcontext;
    String dbName;
    String dbPath;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
        this.dbName = name;
        this.mcontext = context;

        this.dbPath = "/data/data/" + "com.example.propertyresale_app" + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void CheckDb(){
        SQLiteDatabase checkDb = null;
        String filePath = dbPath + dbName;

        try{

            checkDb = SQLiteDatabase.openDatabase(filePath, null, 0);

        }catch (Exception e){
            Toast.makeText(mcontext,e.toString(), Toast.LENGTH_LONG).show();
        }
        if (checkDb != null){
            Toast.makeText(mcontext,"Database connected", Toast.LENGTH_LONG).show();
        }
        else {
            CopyDatabase();
        }
    }

    public void CopyDatabase(){
        this.getReadableDatabase();

        try{
            InputStream ios = mcontext.getAssets().open(dbName);
            OutputStream os = new FileOutputStream(dbPath + dbName);

            byte[] buffer = new byte[1024];

            int len;
            while((len = ios.read(buffer)) > 0){
                os.write(buffer, 0, len);
            }
            os.flush();
            ios.close();
            os.close();

        }catch (Exception e){

            Toast.makeText(mcontext,e.toString(), Toast.LENGTH_LONG).show();
        }
//        log.d("CopuDb", "Database Copied");
    }

    public void OpenDatabase(){
        String filepath = dbPath + dbName;
        SQLiteDatabase.openDatabase(filepath, null, 0);
    }

    public String getpassword(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
        Cursor c = sqLiteDatabase.rawQuery("select password from account_user where name like \"Joey Lim\"", new String[]{});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("password"));
        }
        else{
            return "it is empty";
        }
    }

    public String getusername(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
        Cursor c = sqLiteDatabase.rawQuery("select name from account_user where name like \"Joey Lim\"", new String[]{});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("name"));
        }
        else{
            return "it is empty";
        }
    }

    public Cursor getHousing(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
        Cursor c = sqLiteDatabase.rawQuery("select housing_sn, town, resale_price from housing_list limit 40", null);
        return c;
    }
}
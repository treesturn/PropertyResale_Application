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
import java.util.Objects;

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

//    public String getpassword(String y){
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
////        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
//        Cursor c = sqLiteDatabase.rawQuery("select password from account_user where name like ?", new String[]{y});
//        if (c.moveToFirst()){
//
//            return c.getString(c.getColumnIndex("password"));
//        }
//        else{
//            return "it is empty";
//        }
//    }
//
//    public String getusername(String x){
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
////        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
//        Cursor c = sqLiteDatabase.rawQuery("select name from account_user where name like ?", new String[]{x});
//        if (c.moveToFirst()){
//
//            return c.getString(c.getColumnIndex("name"));
//        }
//        else{
//            return "it is empty";
//        }
//    }

    public Boolean getusername_and_password(String x, String y){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
        Cursor c = sqLiteDatabase.rawQuery("select * from account_user where name = ? and password  = ?", new String[]{x, y});
        if (c.getCount()>0){

            return true;
        }
        else{
            return false;
        }
    }

    public Cursor getHousing(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
        Cursor c = sqLiteDatabase.rawQuery("select housing_sn, town, resale_price from housing_list limit 100", null);
        return c;

    }

    public Cursor gettowns(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        String query = sqLiteDatabase.rawQuery("select password from account_users", null);
        Cursor c = sqLiteDatabase.rawQuery("SELECT DISTINCT town from housing_list;", null);
        return c;
    }




    public String gethousingtype(String sn){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select flat_type_room from housing_list where housing_sn like ?", new String[]{sn});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("flat_type_room"));
        }
        else{
            return "it is empty";
        }
    }

    public String getstoreytype(String sn){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select storey_range from housing_list where housing_sn like ?", new String[]{sn});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("storey_range"));
        }
        else{
            return "it is empty";
        }
    }

    public String getareatype(String sn){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select floor_area_sqm from housing_list where housing_sn like ?", new String[]{sn});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("floor_area_sqm"));
        }
        else{
            return "it is empty";
        }
    }

    public String getmodeltype(String sn){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select flat_model from housing_list where housing_sn like ?", new String[]{sn});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("flat_model"));
        }
        else{
            return "it is empty";
        }
    }

    public String getnumberofinterestedbuyers(String sn){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select interested_buyer from housing_list where housing_sn like ?", new String[]{sn});
        if (c.moveToFirst()){

            return c.getString(c.getColumnIndex("interested_buyer"));
        }
        else{
            return "it is empty";
        }
    }


    /**
     *
     * @param sn
     * @return
     *
     * query 3
     */
    public String getagentinfo(String sn){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT a.name, a.company, a.phone_no\n" +
                "FROM agent a JOIN housing_list h\n" +
                "ON h.license_id = a.license_id\n" +
                "WHERE h.housing_sn = ?", new String[]{sn});

        if (c.moveToFirst()){

            return (c.getString(c.getColumnIndex("name")) + "\n" + c.getString(c.getColumnIndex("company")) + "\n" + c.getString(c.getColumnIndex("phone_no")));
        }
        else{
            return "it is empty";
        }
    }

    /**
     *
     * @param
     * @return
     *
     * query 4
     */
    public String gettransactinfo(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT h.housing_sn, h.town, h.interested_buyer\n" +
                "FROM transact t JOIN housing_list h\n" +
                "ON t.housing_sn = h.housing_sn limit 1;", new String[]{});

        if (c.moveToFirst()){

            return (c.getString(c.getColumnIndex("housing_sn")) + "              " + c.getString(c.getColumnIndex("town")) + "                    " + c.getString(c.getColumnIndex("interested_buyer")));
        }
        else{
            return "it is empty";
        }
    }

    /**
     *
     * @param
     * @return
     *
     * query 2
     */
    public String getavgprice(String town){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT AVG(resale_price) FROM housing_list WHERE town like ? GROUP BY (town);\n", new String[]{town});

        if (c.moveToFirst()){

            return (c.getString(c.getColumnIndex("AVG(resale_price)")));
        }
        else{
            return "it is empty";
        }
    }

    /**
     *
     * @param
     * @return
     *
     * query 5
     */
    public String getpairofsimilarprice(String price){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT h1.housing_sn as sn1, h2.housing_sn as sn2, h1.town as town1, h2.town as town2, h1.floor_area_sqm as area1, \n" +
                "h2.floor_area_sqm as area2, h1.remaining_lease as remaining1, h2.remaining_lease as remaining2 ,h1.resale_price,h2.resale_price\n" +
                "FROM housing_list h1, housing_list h2\n" +
                "WHERE (h1.housing_sn <> h2.housing_sn) AND (h1.town<>h2.town) AND h1.resale_price = h2.resale_price AND h1.resale_price like ? limit 1;",new String[]{price});

        if (c.moveToFirst()){

            return (c.getString(c.getColumnIndex("sn1")) + "          " + c.getString(c.getColumnIndex("town1")) + "          " + c.getString(c.getColumnIndex("area1")) + "          " + c.getString(c.getColumnIndex("remaining1")) + "\n" +
                    c.getString(c.getColumnIndex("sn2")) + "          " + c.getString(c.getColumnIndex("town2")) + "               " + c.getString(c.getColumnIndex("area2")) + "             " + c.getString(c.getColumnIndex("remaining2")));
        }
        else{
            return "it is empty";
        }
    }

}
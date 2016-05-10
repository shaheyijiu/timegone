package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DatabaseAdapter {
    private  String TABLE_NAME = "default_table";
    private  String COLUMN_NAME_SECTION = "timesection";
    private  String COLUMN_NAME_APP = "appname";

    public   String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COLUMN_NAME_SECTION +" TEXT, "
            + COLUMN_NAME_APP  +" TEXT); ";

    public DatabaseAdapter(){

    }

    public void newTable(String table_name,SQLiteDatabase db){
        this.TABLE_NAME = table_name;
        db.execSQL(getSql());
    }

    public String getSql(){
        return USERNAME_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + COLUMN_NAME_SECTION +" TEXT, "
                + COLUMN_NAME_APP  +" TEXT); ";
    }

    public void insertToTable(String section,String appname,SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SECTION,section);
        values.put(COLUMN_NAME_APP,appname);
        db.insert(TABLE_NAME, null, values);
    }

    public void updateTable(String section,String appname,SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SECTION,section);
        values.put(COLUMN_NAME_APP,appname);
        db.insert(TABLE_NAME, null, values);
        String where="COLUMN_NAME_SECTION = ?";
        db.update(TABLE_NAME, values, where, new String[]{section});
    }

    public void queryTable(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " desc",null);
    }
}

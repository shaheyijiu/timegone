package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.util.Log;

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

    private String TAG = "DatabaseAdapter";
    public DatabaseAdapter(){

    }

    public void newTable(String table_name,SQLiteDatabase db) {
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
        values.put("timesection",section);
        values.put("appname", appname);
        db.insert(TABLE_NAME, null, values);
    }

    public void updateTable(String section,String appname,SQLiteDatabase db){
        if(section != null && appname != null){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_SECTION,section);
            values.put(COLUMN_NAME_APP, appname);
            Log.i(TAG, "appname=" + appname);
            if (isFirstInsert(db,section)){
                db.insert(TABLE_NAME, null, values);
            }else {
                String where="timesection = ?" ;
                db.update(TABLE_NAME, values, where, new String[]{section});
            }
            //String sql = "UPDATE "+TABLE_NAME+" SET appname = "+appname+" where timesection = "+section+";";

            //db.execSQL(sql);
        }

    }

    private boolean checkColumnExist1(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        try{
            //查询一行
            cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0"
                    , null );
            result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
        }catch (Exception e){
            Log.e(TAG,"checkColumnExists1..." + e.getMessage()) ;
        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }

        return result ;
    }

    public void queryTable(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME ,null);
        while (cursor.moveToNext()){
            String timesection = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SECTION ));
            String appname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP ));
            Log.i(TAG,"timesection="+timesection+" appname="+appname);
        }
        cursor.close();
    }

    public boolean isFirstInsert(SQLiteDatabase db,String section){
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME ,null);
        while (cursor.moveToNext()){
            String timesection = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SECTION ));
            if (timesection.equals(section))
                return false;
        }
        cursor.close();
        return true;
    }

    public void clearTable(SQLiteDatabase db){
        db.execSQL("DELETE FROM m20160511");
    }
}

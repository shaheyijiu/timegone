package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Util.TextUtil;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DatabaseAdapter {
    private  String SECTION_TABLE_NAME = "";
    private  String COLUMN_NAME_SECTION = "timesection";
    private  String COLUMN_NAME_APP = "appname";
    private String COLUMN_FLAG = "flag";//用于更新table的寻找标记
    private String COLUMN_NAME_TOTAL_TIME = "total_time";

    private String TAG = "DatabaseAdapter";

    public DatabaseAdapter(){

    }

    public void newSectionTable(String table_name,SQLiteDatabase db) {
        this.SECTION_TABLE_NAME = table_name;
        db.execSQL(getSectionCreateSql());
    }

    public void newTotalTimeTable(String table_name,SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + table_name + " ("
                + COLUMN_NAME_APP +" TEXT, "
                + COLUMN_NAME_TOTAL_TIME  +" TEXT); ");
    }

    public String getSectionCreateSql(){
        return  "CREATE TABLE IF NOT EXISTS "
                + SECTION_TABLE_NAME + " ("
                + COLUMN_NAME_SECTION +" SMALLINT, "
                + COLUMN_NAME_APP  +" TEXT,"
                + COLUMN_FLAG + " TEXT); ";
    }

    public void insertToTable(String section,String appname,SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SECTION,section);
        values.put(COLUMN_NAME_APP, appname);
        db.insert(SECTION_TABLE_NAME, null, values);
    }

    public void updateSectionTable(int section,String appname,SQLiteDatabase db,String tablename){
        if( appname != null){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_SECTION,section);
            values.put(COLUMN_NAME_APP, appname);
            values.put(COLUMN_FLAG,Integer.toString(section));
            if (isFirstInsertInSection(db, section, tablename)){
                //Log.i(TAG,"isFisrt");
                db.insert(tablename, null, values);
            }else {
               // String where = COLUMN_NAME_SECTION+" = "+ section ;
                String where = COLUMN_FLAG+" = ?";
                //Log.i(TAG,"where=" +where);
                db.update(tablename, values, where, new String[]{Integer.toString(section)});
            }
        }

    }

    public void updateTotalTimeTable(String appname,Long totalTime,SQLiteDatabase db,String tablename){
        if(totalTime != null && appname != null){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_APP,appname);
            values.put(COLUMN_NAME_TOTAL_TIME, totalTime);
            if (isFirstInsertInTotal(db, appname, tablename)){
                db.insert(tablename, null, values);
            }else {
                String where = COLUMN_NAME_APP+" = ?" ;
                db.update(tablename, values, where, new String[]{appname});
            }
        }
    }

    private boolean checkColumnExist(SQLiteDatabase db, String tableName
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

    public  ArrayList<Map<Integer,String>> querySectionTable(SQLiteDatabase db,String tablename){
        ArrayList<Map<Integer,String>> list = new ArrayList<>();
        if (!tablename.equals("")){
            this.SECTION_TABLE_NAME = tablename;
            db.execSQL(getSectionCreateSql());//防止查询的表不存在
            Cursor cursor = db.rawQuery("select * from " + tablename +" order by "+ COLUMN_NAME_SECTION
                    +" asc" ,null);
            while (cursor.moveToNext()){
                int timesection = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SECTION));
                String appname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP ));
                Map<Integer,String> map = new HashMap<>();
                map.put(timesection,appname);
                list.add(map);
               // Log.i(TAG, "timesection=" + timesection + " appname=" + appname);
            }
            cursor.close();
        }
       // Log.i(TAG, "list=" + list.size());
        return list;
    }

    public Long queryTotalTable(SQLiteDatabase db,String tablename,String appName){
        long totalTime = 0;
        if (!tablename.equals("")){
            newTotalTimeTable(tablename,db);
            Cursor cursor = db.rawQuery("select * from " + tablename ,null);
            if (cursor!=null){
                while (cursor.moveToNext()){
                    String appname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP ));
                    if (appname.equals(appName)){
                        totalTime = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_TOTAL_TIME ));
                    }
                    //Log.i(TAG,"timesection="+timesection+" appname="+appname);
                }
            }

            cursor.close();
        }
        return totalTime;
    }

    public ArrayList<Map<String,Long>> queryAllTotalTable(SQLiteDatabase db,String tablename){
        ArrayList<Map<String,Long>> list = new ArrayList<>();
        if (!tablename.equals("")){
            newTotalTimeTable(tablename,db);
            Cursor cursor = db.rawQuery("select * from " + tablename + " order by "+ COLUMN_NAME_TOTAL_TIME
                    +" asc"  ,null);
            if (cursor!=null){
                while (cursor.moveToNext()){
                    String appname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP ));
                    long totalTime = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_TOTAL_TIME));
                    Map<String,Long> map = new HashMap<>();
                    map.put(appname,totalTime);
                    list.add(map);
                }
            }

            cursor.close();
        }
        return list;
    }

    public boolean isFirstInsertInSection(SQLiteDatabase db,int section,String tablename){
        Cursor cursor = db.rawQuery("select * from " + tablename ,null);
        while (cursor.moveToNext()){
            int timesection = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SECTION));
            if (timesection == section)
                return false;
        }
        cursor.close();
        return true;
    }

    public boolean isFirstInsertInTotal(SQLiteDatabase db,String appName,String tablename){
        Cursor cursor = db.rawQuery("select * from " + tablename ,null);
        while (cursor.moveToNext()){
            String app = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP ));
            if (appName.equals(app))
                return false;
        }
        cursor.close();
        return true;
    }

    public void clearTable(SQLiteDatabase db){
//        db.execSQL("DELETE FROM m20160511");
    }
}

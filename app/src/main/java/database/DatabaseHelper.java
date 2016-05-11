package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SQLITE = "dayData.db3";
    private static int deafultVersion = 1;
    public static DatabaseHelper databaseHelper;

    private String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public  DatabaseHelper(Context context,String name){
        this(context, name, null, deafultVersion);
    }

    public static DatabaseHelper getInstance(Context context){
        if (databaseHelper == null){
            synchronized (DatabaseHelper.class){
                databaseHelper = new DatabaseHelper(context,SQLITE);
            }

        }
        return  databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

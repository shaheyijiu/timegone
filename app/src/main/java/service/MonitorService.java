package service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import database.DatabaseAdapter;
import database.DatabaseHelper;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MonitorService extends Service {
    private String lastPackageName ="";
    private PackageManager pm ;
    private ActivityManager activityManager;

    private ComponentName cn;
    private String TAG ="MonitorService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        activityManager =(ActivityManager) getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        pm = getPackageManager();
        Thread thread = new Thread(new MonitorThread());
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    class MonitorThread implements Runnable{
        private String tablename= "" ;
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(MonitorService.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter dbAdapter= new DatabaseAdapter();

        public String getTableName(){
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            StringBuilder sb = new StringBuilder();
            String tablename = formatter.format(curDate);
            sb.append("m").append(tablename);
            return sb.toString();
        }

        private void saveMap(HashMap<String,Long> map,String section){
            Log.i(TAG, "save进行中 section=" + section);
            Iterator<Map.Entry<String,Long>> iterator = map.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (iterator.hasNext()){
                Map.Entry<String,Long> entry = iterator.next();
                sb.append(entry.getKey()+"66split66");
            }
            Log.i(TAG,"sb="+sb.toString());
            dbAdapter.newTable(tablename,db);
            dbAdapter.updateTable(section, sb.toString(), db);
           // dbAdapter.queryTable(db);
        }

        @Override
        public void run() {
            HashMap<String,Long> map = new HashMap<>();
            Date mDate = new Date();
            long initialTime = mDate.getTime();
            tablename = getTableName();
            Log.i(TAG,"tablename="+tablename);
            dbAdapter.clearTable(db);
            while (true){
                cn = activityManager.getRunningTasks(1).get(0).topActivity;
                String currentPackageName = cn.getPackageName();
                if(!currentPackageName.equals(lastPackageName)){
                    Date date = new Date();
                    long l = 24*60*60*1000; //每天的毫秒数
                    long now = date.getTime();//当前时间
                    int time = (int)((now % l) / (60 * 60 *1000)+8) ;   //计算所属时间段
                    boolean invalidateDay = (now / l ) > (initialTime / l);
                    if (invalidateDay){
                        tablename = getTableName();
                        map.clear();
                        initialTime = now;
                    }
                    switch (time){
                        case 0:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "0");
                            }
                            break;
                        case 1:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "1");
                            }
                            break;
                        case 2:
                            if (!map.containsKey(currentPackageName)) {
                                map.put(currentPackageName, now);
                                saveMap(map, "2");
                            }
                            break;
                        case 3:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "3");
                            }
                            break;
                        case 4:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "4");
                            }
                            break;
                        case 5:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "5");
                            }
                            break;
                        case 6:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "6");
                            }
                            break;
                        case 7:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "7");
                            }
                            break;
                        case 8:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "8");
                            }
                            break;
                        case 9:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "9");
                            }
                            break;
                        case 10:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "10");
                            }
                            break;
                        case 11:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "11");
                            }
                            break;
                        case 12:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "12");
                            }
                            break;
                        case 13:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "13");
                            }
                            break;
                        case 14:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "14");
                            }
                            break;
                        case 15:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "15");
                            }
                            break;
                        case 16:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "16");
                            }
                            break;
                        case 17:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "17");
                            }
                            break;
                        case 18:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "18");
                            }
                            break;
                        case 19:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "19");
                            }
                            break;
                        case 20:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "20");
                            }
                            break;
                        case 21:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "21");
                            }
                            break;
                        case 22:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "22");
                            }
                            break;
                        case 23:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                                saveMap(map, "23");
                            }
                            break;

                    }



//                    try {
//                        ApplicationInfo info = pm.getApplicationInfo(currentPackageName, 0);
//                        Drawable iv = info.loadIcon(pm);
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                lastPackageName = currentPackageName;
            }

        }
    }
}

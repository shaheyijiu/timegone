package service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Util.TextUtil;
import broadcase.WakeService;
import database.DatabaseAdapter;
import database.DatabaseHelper;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MonitorService extends Service {
    private String lastPackageName ="";
    private PackageManager pm ;
    private ActivityManager activityManager;
    private Timer mTimer;
    private ComponentName cn;
    private Intent intent = new Intent("com.example.communication.RECEIVER");

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
        startMonitor();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        WakeService receiver = new WakeService();
        registerReceiver(receiver, filter);
        //flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    public void startMonitor(){
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new MonitorTimer()
                , 2000, 2000);
        }
    }

    public String getTableName(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        StringBuilder sb = new StringBuilder();
        String tablename = formatter.format(curDate);
        sb.append("m").append(tablename);
        TextUtil.putStringValue(this, "tablename", sb.toString());
        return sb.toString();
    }

    class MonitorTimer extends TimerTask{
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(MonitorService.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter dbAdapter= new DatabaseAdapter();
        HashMap<String,Long> map = new HashMap<>();
        Date mDate = new Date();
        long initialTime = mDate.getTime();
        String tablename= getTableName();

        private void saveMap(HashMap<String,Long> map,String section,String tablename){
            Log.i(TAG,"map的大小"+map.size());
            Iterator<Map.Entry<String,Long>> iterator = map.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (iterator.hasNext()){
                Map.Entry<String,Long> entry = iterator.next();
                sb.append(entry.getKey()+"66split66");
            }
            dbAdapter.newTable(tablename, db);
            dbAdapter.updateTable(section, sb.toString(), db,tablename);
            sendBroadcast(intent);//发送广播，更新listview
            //dbAdapter.queryTable(db);
        }

        @Override
        public void run() {
            cn = activityManager.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if(!currentPackageName.equals(lastPackageName)) {
                Date date = new Date();
                long l = 24 * 60 * 60 * 1000; //每天的毫秒数
                long now = date.getTime();//当前时间
                int time = (int) ((now % l) / (60 * 60 * 1000) + 8);   //计算所属时间段
                boolean invalidateDay = (now / l) > (initialTime / l);
                if (invalidateDay) {
                    tablename= getTableName();
                    Log.i(TAG,"invalidateDay启动");
                    map.clear();
                    initialTime = now;
                }
                switch (time) {
                    case 0:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "0", tablename);
                        }
                        break;
                    case 1:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "1", tablename);
                        }
                        break;
                    case 2:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "2", tablename);
                        }
                        break;
                    case 3:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "3", tablename);
                        }
                        break;
                    case 4:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "4", tablename);
                        }
                        break;
                    case 5:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "5", tablename);
                        }
                        break;
                    case 6:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "6", tablename);
                        }
                        break;
                    case 7:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "7", tablename);
                        }
                        break;
                    case 8:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "8", tablename);
                        }
                        break;
                    case 9:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "9", tablename);
                        }
                        break;
                    case 10:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "10", tablename);
                        }
                        break;
                    case 11:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "11", tablename);
                        }
                        break;
                    case 12:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "12", tablename);
                        }
                        break;
                    case 13:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "13", tablename);
                        }
                        break;
                    case 14:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "14", tablename);
                        }
                        break;
                    case 15:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "15", tablename);
                        }
                        break;
                    case 16:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "16", tablename);
                        }
                        break;
                    case 17:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "17", tablename);
                        }
                        break;
                    case 18:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "18", tablename);
                        }
                        break;
                    case 19:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "19", tablename);
                        }
                        break;
                    case 20:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "20", tablename);
                        }
                        break;
                    case 21:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "21", tablename);
                        }
                        break;
                    case 22:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "22", tablename);
                        }
                        break;
                    case 23:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, now);
                            saveMap(map, "23", tablename);
                        }
                        break;
                }
            }
            lastPackageName = currentPackageName;
        }
    }


}

package service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.Log;

import com.kdk.timegone.MainActivity;
import com.kdk.timegone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Util.Constant;
import Util.TextUtil;
import app.App;
import broadcase.WakeService;
import database.DatabaseAdapter;
import database.DatabaseHelper;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MonitorService extends Service {
    private String lastPackageName ="com.kdk.timegone";
    private String defaultSplit = "66split66";
    private PackageManager pm ;
    private ActivityManager activityManager;
    private Timer mTimer;
    private ComponentName cn;
    private Intent intent = new Intent("com.example.communication.RECEIVER");//通知数据库数据更新
    private HashMap<String,Integer> map = new HashMap<>();
    private final static int GRAY_SERVICE_ID = -1001;
    private static MonitorService instance;
    private String TAG ="MonitorService";

    public static MonitorService getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent intent = new Intent(App.getInstance(),  GrayInnerService.class);
            App.getInstance().startService(intent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        initMap();
    }

    private void foregroundWithIcon(){
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("Foreground Service Start");
        builder.setContentTitle("Foreground Service");
        builder.setContentText("Make this service run in the foreground.");
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    /**
     * 防止service被杀死重启后map被清空
     */
    private void initMap(){
        Date mDate = new Date();
        long now = mDate.getTime();
        String tablename = TextUtil.getStringValue(this, "tablename");
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(MonitorService.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter dbAdapter= new DatabaseAdapter();
        ArrayList<Map<Integer,String>> list = dbAdapter.querySectionTable(db, tablename);
        if (list.size() >= 1 && isSameTime(now)){
            Map<Integer,String> tempMap = list.get(list.size() - 1);
            Iterator<Map.Entry<Integer, String>> it = tempMap.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<Integer, String> entry = it.next();
                String[] sArray;
                sArray = entry.getValue().split(defaultSplit);
                for(int i = 0;i < sArray.length;i++){
                    if(!map.containsKey(sArray[i]))
                        map.put(sArray[i], entry.getKey());
                }
            }
        }
    }

    /**
     *判断service挂了重启是否还处于同一时间段
     */
    public boolean isSameTime(long now){
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        int nTime = (int) ((now % l) / (60 * 60 * 1000) + 8);   //计算所属时间段
        Long lastSaveTime = TextUtil.getLongValue(this, Constant.lastSaveTime);
        int lTime = (int) ((lastSaveTime % l) / (60 * 60 * 1000) + 8);
        if (nTime != lTime || (now - lastSaveTime) > l){
            return false;
        } else {
            return true;
        }
    }
    /**
     *判断service挂了重启是否还处于同一天
     */
    public boolean isSameDay(long now){
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        int nTime = (int) ((now % l) / (60 * 60 * 1000) + 8);   //计算所属时间段
        Long lastSaveTime = TextUtil.getLongValue(this, Constant.lastSaveTime);
        int lTime = (int) ((lastSaveTime % l) / (60 * 60 * 1000) + 8);
        if ((now - lastSaveTime) > l){
            return false;
        } else {
            return true;
        }
    }

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
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void startMonitor(){
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

    private String getSectionNewTableName(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        StringBuilder sb = new StringBuilder();
        String tablename = formatter.format(curDate);
        sb.append("m").append(tablename);
        TextUtil.putStringValue(this, "tablename", sb.toString());
        return sb.toString();
    }

    private String getTotalNewTableName(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        StringBuilder sb = new StringBuilder();
        String tablename = formatter.format(curDate);
        sb.append("t").append(tablename);
        TextUtil.putStringValue(this, Constant.totalTimeTableName, sb.toString());
        return sb.toString();
    }



    class MonitorTimer extends TimerTask{
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(MonitorService.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter dbAdapter= new DatabaseAdapter();
        String tablename= getSectionNewTableName();
        String totalTableName = getTotalNewTableName();
        Date date = new Date();
        long initialTime = date.getTime();

        private void saveMap(HashMap<String,Integer> map,int section,
                             String tablename,Long lastSaveTime){
            Log.i(TAG,"map的大小"+map.size());
            TextUtil.putLongValue(App.getInstance(), Constant.lastSaveTime, lastSaveTime);
            Iterator<Map.Entry<String,Integer>> iterator = map.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (iterator.hasNext()){
                Map.Entry<String,Integer> entry = iterator.next();
                sb.append(entry.getKey()+"66split66");
            }
            dbAdapter.newSectionTable(tablename, db);
            dbAdapter.updateSectionTable(section, sb.toString(), db, tablename);
            sendBroadcast(intent);//发送广播，更新listview
        }

        private void saveTotalTime(String tablename,String appName,Long totalTime){
            dbAdapter.newTotalTimeTable(tablename, db);
            dbAdapter.updateTotalTimeTable(appName, totalTime, db, tablename);
        }

        private Long getLastTotalTime(String tablename,String appName){
            return dbAdapter.queryTotalTable(db,tablename,appName);
        }

        @Override
        public void run() {
            cn = activityManager.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            long l = 24 * 60 * 60 * 1000; //每天的毫秒数
            Date mDate = new Date();
            long now = mDate.getTime();//当前时间
            int time = (int) ((now % l) / (60 * 60 * 1000) + 8);   //计算所属时间段
            if (!isSameTime(now)){
                map.clear();
                if (!isSameDay(now)){
                    totalTableName = getTotalNewTableName();
                    saveTotalTime(totalTableName,lastPackageName,
                            getLastTotalTime(totalTableName,lastPackageName)+(now-initialTime));
                    tablename= getSectionNewTableName();
                    initialTime = now;
                }
            }
            if(!currentPackageName.equals(lastPackageName)) {
                saveTotalTime(totalTableName, lastPackageName,
                        getLastTotalTime(totalTableName, lastPackageName) + (now - initialTime));
                //Long t = getLastTotalTime(totalTableName,lastPackageName)+(now-initialTime);
//                Log.i(TAG,"lastPackageName="+lastPackageName+" toatlTime="+t+" now="+now+" initialTime"+
//                        initialTime);
                initialTime = now;
                switch (time) {
                    case 0:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 0);
                            saveMap(map, 0, tablename,now);
                        }
                        break;
                    case 1:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 1);
                            saveMap(map, 1, tablename,now);
                        }
                        break;
                    case 2:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 2);
                            saveMap(map, 2, tablename,now);
                        }
                        break;
                    case 3:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 3);
                            saveMap(map, 3, tablename,now);
                        }
                        break;
                    case 4:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 4);
                            saveMap(map, 4, tablename,now);
                        }
                        break;
                    case 5:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 5);
                            saveMap(map, 5, tablename,now);
                        }
                        break;
                    case 6:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 6);
                            saveMap(map, 6, tablename,now);
                        }
                        break;
                    case 7:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 7);
                            saveMap(map, 7, tablename,now);
                        }
                        break;
                    case 8:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 8);
                            saveMap(map, 8, tablename,now);
                        }
                        break;
                    case 9:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 9);
                            saveMap(map, 9, tablename,now);
                        }
                        break;
                    case 10:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 10);
                            saveMap(map, 10, tablename,now);
                        }
                        break;
                    case 11:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 11);
                            saveMap(map, 11, tablename,now);
                        }
                        break;
                    case 12:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 12);
                            saveMap(map, 12, tablename,now);
                        }
                        break;
                    case 13:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 13);
                            saveMap(map, 13, tablename,now);
                        }
                        break;
                    case 14:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 14);
                            saveMap(map, 14, tablename,now);
                        }
                        break;
                    case 15:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 15);
                            saveMap(map, 15, tablename,now);
                        }
                        break;
                    case 16:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 16);
                            saveMap(map, 16, tablename,now);
                        }
                        break;
                    case 17:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 17);
                            saveMap(map, 17, tablename,now);
                        }
                        break;
                    case 18:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 18);
                            saveMap(map, 18, tablename,now);
                        }
                        break;
                    case 19:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 19);
                            saveMap(map, 19, tablename,now);
                        }
                        break;
                    case 20:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 20);
                            saveMap(map, 20, tablename,now);
                        }
                        break;
                    case 21:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 21);
                            saveMap(map, 21, tablename,now);
                        }
                        break;
                    case 22:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName, 22);
                            saveMap(map, 22, tablename,now);
                        }
                        break;
                    case 23:
                        if (!map.containsKey(currentPackageName)) {
                            map.put(currentPackageName,23);
                            saveMap(map, 23, tablename,now);
                        }
                        break;
                }
            }
            lastPackageName = currentPackageName;
        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            try {
                MonitorService fakeService = MonitorService.getInstance();
                fakeService.startForeground(GRAY_SERVICE_ID, new Notification());
                startForeground(GRAY_SERVICE_ID, new Notification());
                fakeService.stopForeground(true);
            } catch (Exception e) {

            }
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }
}

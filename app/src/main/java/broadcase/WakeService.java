package broadcase;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kdk.timegone.MainActivity;
import com.kdk.timegone.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import Util.Constant;
import Util.TextUtil;
import app.App;
import database.DatabaseAdapter;
import database.DatabaseHelper;
import service.MonitorService;

/**
 * Created by Administrator on 2016/5/16.
 */
public class WakeService extends BroadcastReceiver {
    private String TAG = "WakeService";
    private static final int NOTIFICATION_FLAG = 10;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isServiceRunning = false;
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            //检查Service状态
            ActivityManager manager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("service.MonitorService".equals(service.service.getClassName())) {
                    isServiceRunning = true;
                }
            }
            if (!isServiceRunning) {

                Intent i = new Intent(context, MonitorService.class);
                context.startService(i);
            }
            Log.i(TAG, "serviceNanme");
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(App.getInstance());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            DatabaseAdapter dbAdapter= new DatabaseAdapter();
            ActivityManager activityManager =(ActivityManager) App.getInstance().getSystemService(
                    Context.ACTIVITY_SERVICE);
            PackageManager pm = App.getInstance().getPackageManager();
            ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            String name = null;
            try {
                name = pm.getApplicationLabel(
                        pm.getApplicationInfo(currentPackageName,
                                PackageManager.GET_META_DATA)).toString();

                int limitTime = dbAdapter.queryLimitTable(db, Constant.limitTable,name);
                Log.i(TAG,"name= "+name+" limitTime= "+limitTime);
                if (limitTime != 0){
                    long totalTime = (dbAdapter.queryTotalTable(db,TextUtil.getStringValue(App.getInstance(),Constant.totalTimeTableName),currentPackageName) / (1000*60));
                    Log.i(TAG,"name= "+name+" limitTime= "+limitTime+" totalTime= "+totalTime);
                    if (totalTime > limitTime){
                        sendNotification(name+"使用时间超过日限了");
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNotification(String msg){
        NotificationManager manager = (NotificationManager) App.getInstance().
                getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(App.getInstance(), 0,
                new Intent(App.getInstance(), MainActivity.class), 0);
        Notification notify2 = new Notification.Builder(App.getInstance())
                .setSmallIcon(R.drawable.ic_launcher) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                        // icon)
                .setTicker(msg)// 设置在status
                        // bar上显示的提示文字
                .setContentTitle("Notification Title")// 设置在下拉status
                        // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentText(msg)// TextView中显示的详细内容
                .setContentIntent(pendingIntent2) // 关联PendingIntent
                .setNumber(1) // 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
                .getNotification(); // 需要注意build()是在API level
        // 16及之后增加的，在API11中可以使用getNotificatin()来代替
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notify2);
    }
}

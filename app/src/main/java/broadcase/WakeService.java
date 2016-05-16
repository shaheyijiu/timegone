package broadcase;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import app.App;
import service.MonitorService;

/**
 * Created by Administrator on 2016/5/16.
 */
public class WakeService extends BroadcastReceiver {
    private String TAG = "WakeService";
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isServiceRunning = false;
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            //检查Service状态
            ActivityManager manager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("service.MonitorService".equals(service.service.getClassName())) {
                    Log.i(TAG,"serviceNanme="+service.service.getClassName());
                    isServiceRunning = true;
                }
            }
            if (!isServiceRunning) {
                Log.i(TAG,"serviceNanme被杀");
                //Intent i = new Intent(context, MonitorService.class);
                //context.startService(i);
            }


        }
    }
}

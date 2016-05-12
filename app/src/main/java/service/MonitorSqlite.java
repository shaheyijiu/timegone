package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ServiceConfigurationError;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/12.
 */
public class MonitorSqlite extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 监控数据库数据的变化
     */
    class MonitorTimer extends TimerTask{

        @Override
        public void run() {

        }
    }
}

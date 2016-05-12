package app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2016/5/12.
 */
public class App extends Application {
    private  static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        instance = this;

    }
    public static App getInstance(){
        return instance;
    }

}


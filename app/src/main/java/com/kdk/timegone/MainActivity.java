package com.kdk.timegone;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.AlphaAnimatorAdapter;
import adapter.BottomAnimatorAdapter;
import adapter.BottomRightAnimatorAdapter;
import adapter.LeftAnimatorAdapter;
import adapter.MyListAdapter;
import adapter.RightAnimatorAdapter;
import adapter.ScaleAnimatorAdapter;

public class MainActivity extends Activity {
    private String TAG = "MainActivity";
    private ListView listView;
    private ArrayList<String> list = new ArrayList<>();
    private BaseAdapter mAdapter;
    private ActivityManager activityManager;
    private PackageManager pm ;
    private ComponentName cn;
    private String lastPackageName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list);
        for(int i = 0;i < 100;i++){
            list.add("这是第"+i+"个项");
        }
        MyListAdapter adapter = new MyListAdapter(this,R.layout.listview_item,list);
        //setAlphaAnimator(adapter);
        setLeftAnimator(adapter);
        //setRightAnimator(adapter);
        //setScaleAnimator(adapter);
        //setBotoomAnimator(adapter);
        //setBottomRightAnimator(adapter);
        activityManager =(ActivityManager) getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        pm = MainActivity.this.getPackageManager();
        Thread thread = new Thread(new MonitorThread());
        thread.start();
    }

    private void setAlphaAnimator(BaseAdapter adapter){
        AlphaAnimatorAdapter alphaAdapter = new AlphaAnimatorAdapter();
        alphaAdapter.setAdapter(adapter);
        alphaAdapter.setAbsListView(listView);
        listView.setAdapter(alphaAdapter);
    }

    private void setLeftAnimator(BaseAdapter adapter){
        LeftAnimatorAdapter leftAdapter = new LeftAnimatorAdapter();
        leftAdapter.setAdapter(adapter);
        leftAdapter.setAbsListView(listView);
        listView.setAdapter(leftAdapter);
    }

    private void setRightAnimator(BaseAdapter adapter){
        RightAnimatorAdapter rightAdapter = new RightAnimatorAdapter();
        rightAdapter.setAdapter(adapter);
        rightAdapter.setAbsListView(listView);
        listView.setAdapter(rightAdapter);
    }

    private void setScaleAnimator(BaseAdapter adapter){
        ScaleAnimatorAdapter scaleAdapter = new ScaleAnimatorAdapter();
        scaleAdapter.setAdapter(adapter);
        scaleAdapter.setAbsListView(listView);
        scaleAdapter.setScaleFrom(0.5f);
        listView.setAdapter(scaleAdapter);
    }

    private void setBotoomAnimator(BaseAdapter adapter){
        BottomAnimatorAdapter bottomAdapter = new BottomAnimatorAdapter();
        bottomAdapter.setAdapter(adapter);
        bottomAdapter.setAbsListView(listView);
        listView.setAdapter(bottomAdapter);
    }

    private void setBottomRightAnimator(BaseAdapter adapter){
        BottomRightAnimatorAdapter bottomRightAdapter = new  BottomRightAnimatorAdapter();
        bottomRightAdapter.setAdapter(adapter);
        bottomRightAdapter.setAbsListView(listView);
        listView.setAdapter(bottomRightAdapter);
    }

    class MonitorThread implements Runnable{

        @Override
        public void run() {
            while (true){
                cn = activityManager.getRunningTasks(1).get(0).topActivity;
                String currentPackageName = cn.getPackageName();
                if(!currentPackageName.equals(lastPackageName)){
                    Log.i(TAG,"currentPackageName"+currentPackageName);
                    try {
                        ApplicationInfo info = pm.getApplicationInfo(currentPackageName, 0);
                        Drawable iv = info.loadIcon(pm);
                        if(iv != null){
                            Log.i(TAG,"获得drawable");
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                lastPackageName = currentPackageName;
            }

        }
    }

}

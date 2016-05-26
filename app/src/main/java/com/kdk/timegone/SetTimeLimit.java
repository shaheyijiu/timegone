package com.kdk.timegone;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.Constant;
import Util.TextUtil;
import adapter.TimeLimitAdapter;
import app.App;

/**
 * Created by Administrator on 2016/5/24.
 */
public class SetTimeLimit extends Activity {
    public static int currentPosition = -1;
    private ListView listView;

    private String TAG = "SetTimeLimit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitTheme();
        setContentView(R.layout.layout_time_limit);
        listView = (ListView)findViewById(R.id.list);
        List<Map<String,Drawable>> mList = getAPPInstalled();
        final TimeLimitAdapter mAdapter = new TimeLimitAdapter(this,R.layout.listview_item_setting,mList,listView);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private List<Map<String,Drawable>> getAPPInstalled(){
        List<Map<String,Drawable>> mList = new ArrayList<>();
        List<PackageInfo> packageinfo = new ArrayList<>();
        // 获取系统内的所有程序信息
        Intent mainintent = new Intent(Intent.ACTION_MAIN, null);
        mainintent.addCategory(Intent.CATEGORY_LAUNCHER);
        packageinfo = App.getInstance().getPackageManager().getInstalledPackages(0);
        PackageManager pm = App.getInstance().getPackageManager();
        int count = packageinfo.size();
        for(int i=0; i< count; i++){
            PackageInfo pinfo = packageinfo.get(i);
            ApplicationInfo appInfo = pinfo.applicationInfo;
            if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                //系统程序 忽略
            }else{
                Map<String,Drawable> map = new HashMap<>();
                String name = null;
                try {
                    name = pm.getApplicationLabel(
                            pm.getApplicationInfo(pinfo.applicationInfo.packageName,
                                    PackageManager.GET_META_DATA)).toString();
                    map.put(name,appInfo.loadIcon(pm));
                    mList.add(map);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return mList;

    }

    private void InitTheme(){
        if (TextUtil.getIntValue(App.getInstance(), Constant.theme) == 1){
            this.setTheme(R.style.theme_light);
        } else {
            TextUtil.putIntValue(App.getInstance(), Constant.theme, 1);
            this.setTheme(R.style.theme_night);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"destory");
        currentPosition = -1;
    }
}

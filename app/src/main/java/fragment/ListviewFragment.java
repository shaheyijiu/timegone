package fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kdk.timegone.MainActivity;
import com.kdk.timegone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import adapter.AlphaAnimatorAdapter;
import adapter.BottomAnimatorAdapter;
import adapter.BottomRightAnimatorAdapter;
import adapter.LeftAnimatorAdapter;
import adapter.MyListAdapter;
import adapter.RightAnimatorAdapter;
import adapter.ScaleAnimatorAdapter;
import database.DatabaseAdapter;
import database.DatabaseHelper;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ListviewFragment extends Fragment {
    private ArrayList<String> list = new ArrayList<>();
    private PackageManager pm ;
    private ActivityManager activityManager;
    private String lastPackageName ="";
    private ComponentName cn;
    private ListView listView;
    private View layout;

    private String TAG = "ListviewFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        for(int i = 0;i < 100;i++){
            list.add("这是第"+i+"个项");
        }
        layout = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_listview,null);
        listView = (ListView)layout.findViewById(R.id.list);
        MyListAdapter adapter = new MyListAdapter(getContext(), R.layout.listview_item,list);
        setLeftAnimator(adapter);

        activityManager =(ActivityManager) getActivity().getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        pm = getActivity().getPackageManager();
        Thread thread = new Thread(new MonitorThread());
        thread.start();
        return layout;
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
        private String tablename= "" ;
        public String getTableName(){
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String tablename = formatter.format(curDate);
            return tablename;
        }

        private void saveMap(HashMap<String,Long> map,String section){
            Iterator<Map.Entry<String,Long>> iterator = map.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (iterator.hasNext()){
                Map.Entry<String,Long> entry = iterator.next();
                sb.append(entry.getKey()+"66split66");
            }
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(getContext());
            DatabaseAdapter dbAdapter= new DatabaseAdapter();
            dbAdapter.newTable(tablename,dbHelper.getSQLiteDatabase());
            dbAdapter.updateTable(section,sb.toString(),dbHelper.getSQLiteDatabase());
        }

        @Override
        public void run() {
            HashMap<String,Long> map = new HashMap<>();
            Date mDate = new Date();
            long initialTime = mDate.getTime();
            tablename = getTableName();
            while (true){
                cn = activityManager.getRunningTasks(1).get(0).topActivity;
                String currentPackageName = cn.getPackageName();
                if(!currentPackageName.equals(lastPackageName)){
                    Date date = new Date();
                    long l = 24*60*60*1000; //每天的毫秒数
                    long now = date.getTime();//当前时间
                    int time = (int)(date.getTime() - (date.getTime() % l) -
                            8* 60 * 60 *1000) / (60 * 60 *1000) ;   //计算所属时间段
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
                            }
                            saveMap(map,"0");
                            break;
                        case 1:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"1");
                            break;
                        case 2:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"2");
                            break;
                        case 3:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"3");
                            break;
                        case 4:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"4");
                            break;
                        case 5:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"5");
                            break;
                        case 6:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"6");
                            break;
                        case 7:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"7");
                            break;
                        case 8:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"8");
                            break;
                        case 9:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"9");
                            break;
                        case 10:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"10");
                            break;
                        case 11:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"11");
                            break;
                        case 12:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"12");
                            break;
                        case 13:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"13");
                            break;
                        case 14:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"14");
                            break;
                        case 15:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"15");
                            break;
                        case 16:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"16");
                            break;
                        case 17:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"17");
                            break;
                        case 18:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"18");
                            break;
                        case 19:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"19");
                            break;
                        case 20:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"20");
                            break;
                        case 21:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"21");
                            break;
                        case 22:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"22");
                            break;
                        case 23:
                            if (!map.containsKey(currentPackageName)){
                                map.put(currentPackageName,now);
                            }
                            saveMap(map,"23");
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

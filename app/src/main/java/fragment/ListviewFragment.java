package fragment;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import Util.TextUtil;
import adapter.AlphaAnimatorAdapter;
import adapter.BottomAnimatorAdapter;
import adapter.BottomRightAnimatorAdapter;
import adapter.LeftAnimatorAdapter;
import adapter.MyListAdapter;
import adapter.RightAnimatorAdapter;
import adapter.ScaleAnimatorAdapter;
import database.DatabaseAdapter;
import database.DatabaseHelper;
import service.MonitorService;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ListviewFragment extends Fragment {
    private Map<String,String> mapSqlite = new HashMap<>();
    private ListView listView;
    private View layout;
    private  ArrayList<Map<String,String>> mList = new ArrayList<>();
    private MyListAdapter adapter;
    private MsgReceiver msgReceiver;
    private LeftAnimatorAdapter leftAdapter;
    private String TAG = "ListviewFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_listview,null);
        listView = (ListView)layout.findViewById(R.id.list);
        initList();

        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.communication.RECEIVER");
        getActivity().registerReceiver(msgReceiver, intentFilter);

        Intent intent = new Intent(getActivity(), MonitorService.class);
        getActivity().startService(intent);
        return layout;
    }

    private void initList(){
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter mAdapter = new DatabaseAdapter();
        String tablename = TextUtil.getStringValue(getContext(),"tablename");
        mList = mAdapter.queryTable(db,tablename);
        adapter = new MyListAdapter(getContext(), R.layout.listview_item,mList);
        //listView.setAdapter(adapter);
        setLeftAnimator(adapter);
    }

    private void updateListview(MyListAdapter adapter){
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter mAdapter = new DatabaseAdapter();
        String tablename = TextUtil.getStringValue(getContext(),"tablename");
        mList = mAdapter.queryTable(db,tablename);
        adapter = new MyListAdapter(getContext(), R.layout.listview_item,mList);
        initList();
        //adapter.notifyDataSetChanged();
    }


    private void setAlphaAnimator(BaseAdapter adapter){
        AlphaAnimatorAdapter alphaAdapter = new AlphaAnimatorAdapter();
        alphaAdapter.setAdapter(adapter);
        alphaAdapter.setAbsListView(listView);
        listView.setAdapter(alphaAdapter);
    }

    private void setLeftAnimator(BaseAdapter adapter){
        leftAdapter = new LeftAnimatorAdapter();
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

    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            updateListview(adapter);
           // abortBroadcast();
        }

    }
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(msgReceiver);
        super.onDestroy();
    }
}

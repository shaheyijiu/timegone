package fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import service.MonitorService;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ListviewFragment extends Fragment {
    private ArrayList<String> list = new ArrayList<>();
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

        Intent intent = new Intent(getActivity(), MonitorService.class);
        getActivity().startService(intent);
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


}

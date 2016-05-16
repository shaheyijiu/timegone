package adapter;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.kdk.timegone.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Administrator on 2016/4/21.
 */
public class MyListAdapter extends ArrayAdapter {
    private String TAG = "MyListAdapter";
    private Context mContext;
    private int res;
    private  ArrayList<Map<String,String>> mList;
    private LinearLayoutManager mLayoutManager;
    private PackageManager pm ;
    private String defaultSplit = "66split66";
    private String[] ImageBox;
    private Drawable[] DrawableBox;
    public MyListAdapter(Context context, int resource,ArrayList<Map<String,String>> mList) {
        super(context,resource);
        this.mContext = context;
        this.res = resource;
        this.mList = mList;
        pm = context.getPackageManager();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView =  LayoutInflater.from(mContext).inflate(res, parent, false);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.list_row);
            viewHolder.mRecyclerView = (RecyclerView)convertView.findViewById(R.id.my_recycler_view);
            viewHolder.mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mLayoutManager.setOrientation(0);
            viewHolder.mRecyclerView.setLayoutManager(mLayoutManager);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(getSection(getKey(mList.get(position))));

        getValue(mList.get(position));
        RecyclerAdapter mAdapter = new RecyclerAdapter(getIcon(ImageBox));
        viewHolder.mRecyclerView.setAdapter(mAdapter);
        return convertView;

    }
    private String getSection(String s){
        switch(s){
            case "0":
                return "00:00 - 00:59";
            case "1":
                return "01:00 - 01:59";
            case "2":
                return "02:00 - 02:59";
            case "3":
                return "03:00 - 03:59";
            case "4":
                return "04:00 - 04:59";
            case "5":
                return "05:00 - 05:59";
            case "6":
                return "06:00 - 06:59";
            case "7":
                return "07:00 - 07:59";
            case "8":
                return "08:00 - 08:59";
            case "9":
                return "09:00 - 09:59";
            case "10":
                return "10:00 - 10:59";
            case "11":
                return "11:00 - 11:59";
            case "12":
                return "12:00 - 12:59";
            case "13":
                return "13:00 - 13:59";
            case "14":
                return "14:00 - 14:59";
            case "15":
                return "15:00 - 15:59";
            case "16":
                return "16:00 - 16:59";
            case "17":
                return "17:00 - 17:59";
            case "18":
                return "18:00 - 18:59";
            case "19":
                return "19:00 - 19:59";
            case "20":
                return "20:00 - 20:59";
            case "21":
                return "21:00 - 21:59";
            case "22":
                return "22:00 - 22:59";
            case "23":
                return "23:00 - 23:59";
            default:
                return null;
        }
    }

    private String getKey(Map<String,String> map){
        Iterator iterator = map.entrySet().iterator();
        StringBuilder sb  = new StringBuilder();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey());
        }
        return sb.toString();
    }

    private void getValue(Map<String,String> map){
        Iterator iterator = map.entrySet().iterator();
        StringBuilder sb  = new StringBuilder();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String s = (String)entry.getValue();
            if ((!s.equals(""))){
                sb.append(entry.getValue());
            }
        }
        String s = sb.toString();
        ImageBox = s.split(defaultSplit);
    }

    private Drawable[] getIcon(String[] ImageBox){
        DrawableBox = new Drawable[ImageBox.length];
        for (int i = 0;i < ImageBox.length;i++){
            try {
                if(!ImageBox[i].equals("com.smartisanos.launcher")){
                    ApplicationInfo info = pm.getApplicationInfo(ImageBox[i], 0);
                    DrawableBox[i] = info.loadIcon(pm);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return DrawableBox;
    }

    public static class ViewHolder{
        private TextView textView;
        private RecyclerView mRecyclerView;
    }
}

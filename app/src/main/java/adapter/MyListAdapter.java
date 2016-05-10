package adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kdk.timegone.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/21.
 */
public class MyListAdapter extends ArrayAdapter {
    private String TAG = "MyListAdapter";
    private Context mContext;
    private int res;
    private ListView listView;
    private ArrayList<String> list = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int[] ImageBox ={
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,};

    public MyListAdapter(Context context, int resource,ArrayList<String> list) {
        super(context,resource);
        this.mContext = context;
        this.res = resource;
        this.list = list;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return list.size();
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
        viewHolder.textView.setText(list.get(position));
        RecyclerAdapter mAdapter = new RecyclerAdapter(ImageBox);
        viewHolder.mRecyclerView.setAdapter(mAdapter);
        return convertView;

    }

    public static class ViewHolder{
        private TextView textView;
        private RecyclerView mRecyclerView;
    }
}

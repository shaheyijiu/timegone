package adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

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

        if (convertView == null) {
            convertView =  LayoutInflater.from(mContext).inflate(res, parent, false);
        }
        TextView view = (TextView)convertView.findViewById(R.id.list_row);
        view.setText(list.get(position));

        return convertView;

    }

    public void setListview(ListView listview){
        this.listView = listview;
    }
}

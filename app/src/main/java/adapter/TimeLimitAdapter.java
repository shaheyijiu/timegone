package adapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdk.timegone.R;
import com.kdk.timegone.SetTimeLimit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

import Util.Constant;
import database.DatabaseAdapter;
import database.DatabaseHelper;

/**
 * Created by Administrator on 2016/5/24.
 */
public class TimeLimitAdapter extends ArrayAdapter{
    private List<Map<String,Drawable>> mList;
    private Context mContext;
    private int resource;
    private ListView listView;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private DatabaseAdapter dbAdapter;

    public TimeLimitAdapter(Context context, int resource,List<Map<String,Drawable>> mList,ListView listView) {
        super(context, resource);
        this.mContext = context;
        this.resource = resource;
        this.mList = mList;
        this.listView = listView;
        dbHelper = DatabaseHelper.getInstance(this.mContext);
        db = dbHelper.getWritableDatabase();
        dbAdapter= new DatabaseAdapter();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView =  LayoutInflater.from(mContext).inflate(resource, parent, false);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_appIcon);
            viewHolder.pName = (TextView)convertView.findViewById(R.id.tv_appName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Map<String,Drawable> map = mList.get(position);
        viewHolder.imageView.setImageDrawable(getAppIcon(map));
        viewHolder.pName.setText(getAppName(map));

        final TextView tv_limit = (TextView)convertView.findViewById(R.id.tv_limit);
        final RelativeLayout rl_bar = (RelativeLayout)convertView.findViewById(R.id.rl_bar);
        int limitTime = dbAdapter.queryLimitTable(db,Constant.limitTable,getAppName(map));
        if (limitTime == 0){
            tv_limit.setText("日限");
        } else {
            tv_limit.setText(Integer.toString(limitTime));
        }
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String text = (String)msg.obj;
                tv_limit.setText(text);

            }
        };
        if (position == SetTimeLimit.currentPosition){
            if (rl_bar.getVisibility() == View.GONE){
                rl_bar.setVisibility(View.VISIBLE);
                viewHolder.minus = (Button)convertView.findViewById(R.id.minus);
                viewHolder.add = (Button)convertView.findViewById(R.id.add);
                viewHolder.sure = (Button)convertView.findViewById(R.id.bt_sure);
                final ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.progressBar);
                final RelativeLayout rl = (RelativeLayout)convertView.findViewById(R.id.rl_bar);
                final String temp = (String)tv_limit.getText();
                int mValue = 0;
                try{
                    mValue = Integer.parseInt(temp);
                } catch (Exception e){
                    mValue= 0;
                }
                pb.setProgress(mValue);
                viewHolder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pb.incrementProgressBy(-1);
                        pb.setProgress(pb.getProgress());
                        if (pb.getProgress() == 0){
                            Message message = new Message();
                            message.obj = "日限";
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = Integer.toString(pb.getProgress());
                            handler.sendMessage(message);
                        }
                    }
                });
                viewHolder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pb.incrementProgressBy(1);
                        pb.setProgress(pb.getProgress());
                        if (pb.getProgress() == 0){
                            Message message = new Message();
                            message.obj = "日限";
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = Integer.toString(pb.getProgress());
                            handler.sendMessage(message);
                        }
                    }
                });
                viewHolder.sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String temp = (String)tv_limit.getText();
                        int mValue = 0;
                        try{
                            mValue = Integer.parseInt(temp);
                        } catch (Exception e){
                            mValue= 0;
                        }
                        rl.setVisibility(View.GONE);
                        dbAdapter.updateLimitTimeTable(getAppName(map),mValue,db, Constant.limitTable);
                    }
                });
            }
        } else {
            rl_bar.setVisibility(View.GONE);
        }



        return convertView;
    }

    private String getAppName(Map<String,Drawable> map){
        Iterator<Map.Entry<String,Drawable>> iterator = map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while(iterator.hasNext()){
            Map.Entry<String,Drawable> entry = iterator.next();
            sb.append(entry.getKey());
        }
        return sb.toString();
    }

    private Drawable getAppIcon(Map<String,Drawable> map){
        Iterator<Map.Entry<String,Drawable>> iterator = map.entrySet().iterator();
        Map.Entry<String,Drawable> entry = iterator.next();
        return entry.getValue();
    }

    public static class ViewHolder{
        private ImageView imageView;
        private TextView pName;
        private RelativeLayout rl_bar;
        private Button minus;
        private Button add;
        private Button sure;

    }
}

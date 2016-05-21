package fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kdk.timegone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Util.Constant;
import Util.TextUtil;
import app.App;
import database.DatabaseAdapter;
import database.DatabaseHelper;
import view.PieChartView;

/**
 * Created by Administrator on 2016/5/5.
 */
public class GraphFragment extends Fragment {
    private View layout;
    private ArrayList<Map<String,Long>> mList;
    List<PieChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();
    private int[] colors ={0xFF11AA33,Color.GRAY,Color.GREEN,Color.RED,Color.BLUE,Color.BLACK,Color.CYAN};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_graph,null);
        PieChartView pieChartView = (PieChartView) layout.findViewById(R.id.pie_chart);
        initGraph();
        pieChartView.setData(pieceDataHolders);
        return layout;
    }

    private void initGraph(){
        mList = getTotalTime();
       if (mList.size() < 4){
            for (int i = 0;i < mList.size();i++){
                Map<String,Long> map = mList.get(i);
                String appPackageName = getPackageName(map);
                String appProgramName = getProgramName(App.getInstance(),appPackageName);
                Long time = getTime(map);
                pieceDataHolders.add(new PieChartView.PieceDataHolder(time,colors[i], appProgramName));
            }
       } else{
           for (int i = 1;i <= 4;i++){
               Map<String,Long> map = mList.get(mList.size()-i);
               String appPackageName = getPackageName(map);
               String appProgramName = getProgramName(App.getInstance(),appPackageName);
               Long time = getTime(map);
               pieceDataHolders.add(new PieChartView.PieceDataHolder(time,colors[i], appProgramName));
           }
           long total = 0;
           for (int i = 0;i < mList.size()-4;i++){
               Map<String,Long> map = mList.get(i);
               total += getTime(map);
           }
           pieceDataHolders.add(new PieChartView.PieceDataHolder(total,colors[0], "其它"));
       }

//        pieceDataHolders.add(new PieChartView.PieceDataHolder(1000, 0xFF11AA33, "明天，２"));
//        pieceDataHolders.add(new PieChartView.PieceDataHolder(1200, Color.GRAY, "就是风，３"));
//        pieceDataHolders.add(new PieChartView.PieceDataHolder(5000, Color.YELLOW, "呵呵，４"));
//        pieceDataHolders.add(new PieChartView.PieceDataHolder(10000, Color.RED, "小京，５"));
//        pieceDataHolders.add(new PieChartView.PieceDataHolder(13000, Color.BLUE, "花花，６"));
    }

    private ArrayList<Map<String,Long>> getTotalTime(){
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(App.getInstance());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatabaseAdapter dbAdapter= new DatabaseAdapter();
        ArrayList<Map<String,Long>> list = dbAdapter.queryAllTotalTable(db,
                TextUtil.getStringValue(App.getInstance(),Constant.totalTimeTableName));
        return list;
    }

    private Long getTime(Map<String,Long> map){
        Iterator<Map.Entry<String,Long> > iterator = map.entrySet().iterator();
        long t = 0;
        while (iterator.hasNext()){
            Map.Entry<String,Long> entry = iterator.next();
            t = entry.getValue();
        }
        return t;
    }

    private String getPackageName(Map<String,Long> map){
        Iterator<Map.Entry<String,Long> > iterator = map.entrySet().iterator();
        String s = "";
        while (iterator.hasNext()){
            Map.Entry<String,Long> entry = iterator.next();
            s = entry.getKey();
        }
        return s;
    }
    /**
     * 获得应用的名称
     * @param context
     * @param packageName
     * @return
     */
    public static String getProgramName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = null;
        try {
            name = pm.getApplicationLabel(
                    pm.getApplicationInfo(packageName,
                            PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }


}

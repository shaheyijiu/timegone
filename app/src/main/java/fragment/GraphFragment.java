package fragment;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kdk.timegone.R;

import java.util.ArrayList;
import java.util.List;

import view.PieChartView;

/**
 * Created by Administrator on 2016/5/5.
 */
public class GraphFragment extends Fragment {
    private View layout;
    List<PieChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();
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
        pieceDataHolders.add(new PieChartView.PieceDataHolder(100,0xFF77CCAA, "今天，１"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(1000, 0xFF11AA33, "明天，２"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(1200, Color.GRAY, "就是风，３"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(5000, Color.YELLOW, "呵呵，４"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(10000, Color.RED, "小京，５"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(13000, Color.BLUE, "花花，６"));
    }
}

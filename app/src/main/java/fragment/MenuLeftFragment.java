package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kdk.timegone.R;

/**
 * Created by Administrator on 2016/5/21.
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout timeUse;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.layout_menu, container, false);
    }

    private void initView(View view){
        timeUse = (RelativeLayout)view.findViewById(R.id.timeUse);
    }

    private void setListener(){
        timeUse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.timeUse){

        }
    }
}

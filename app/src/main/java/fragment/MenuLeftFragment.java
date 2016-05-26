package fragment;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kdk.timegone.R;
import com.kdk.timegone.SetTimeLimit;

import Util.Constant;
import Util.TextUtil;
import app.App;
import colorUi.util.ColorUiUtil;
import colorUi.util.SharedPreferencesMgr;

/**
 * Created by Administrator on 2016/5/21.
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout timeUse;
    private RelativeLayout nightMode;

    private String TAG = "MenuLeftFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_menu, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view){
        timeUse = (RelativeLayout)view.findViewById(R.id.timeUse);
        nightMode = (RelativeLayout)view.findViewById(R.id.nightMode);
    }

    private void setListener(){
        timeUse.setOnClickListener(this);
        nightMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.timeUse){
            Intent intent = new Intent(getActivity(), SetTimeLimit.class);
            startActivity(intent);
        }
        if (id == R.id.nightMode){

            if (TextUtil.getIntValue(App.getInstance(), Constant.theme) == 1){
                TextUtil.putIntValue(App.getInstance(), Constant.theme, 2);
                getActivity().setTheme(R.style.theme_night);
            } else {
                TextUtil.putIntValue(App.getInstance(), Constant.theme, 1);
                getActivity().setTheme(R.style.theme_light);
            }
            final View rootView = getActivity().getWindow().getDecorView();
            if(Build.VERSION.SDK_INT >= 14) {
                rootView.setDrawingCacheEnabled(true);
                rootView.buildDrawingCache(true);
                final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                rootView.setDrawingCacheEnabled(false);
                if (null != localBitmap && rootView instanceof ViewGroup) {
                    final View localView2 = new View(App.getInstance());
                    localView2.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ((ViewGroup) rootView).addView(localView2, params);
                    localView2.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            ColorUiUtil.changeTheme(rootView, getActivity().getTheme());
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ((ViewGroup) rootView).removeView(localView2);
                            localBitmap.recycle();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                }
            } else {
                ColorUiUtil.changeTheme(rootView, getActivity().getTheme());
            }
        }
    }
}
